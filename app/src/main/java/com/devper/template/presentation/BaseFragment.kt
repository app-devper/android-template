package com.devper.template.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.SESSION_EXPIRED_ERROR
import com.devper.template.R
import com.devper.template.core.platform.EventObserver
import com.devper.template.domain.core.ErrorMapper

abstract class BaseFragment<Binding : ViewDataBinding>(private val layoutId: Int) : Fragment() {

    lateinit var binding: Binding
    abstract val viewModel: BaseViewModel
    protected val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            onNavigate = { id, bundle ->
                mainViewModel.navigate(id, bundle)
            }
            retry = {
                getArgument()
            }
            popBackStack = {
                findNavController().popBackStack()
            }
        }
        setupView()
        observe()
        observeLiveData()
        getArgument()
    }

    private fun getArgument() {
        viewModel.flow = arguments?.getString(EXTRA_FLOW)
        onArguments(arguments)
    }

    private fun observe() {
        mainViewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver {
            showMessage(code = it.first, message = it.second)
        })
        mainViewModel.codeLiveData.observe(viewLifecycleOwner, EventObserver {
            handleError(it)
        })
    }

    abstract fun setupView()

    abstract fun observeLiveData()

    abstract fun onArguments(it: Bundle?)

    private fun handleError(code: String) {
        if (code == SESSION_EXPIRED_ERROR) {
            viewModel.onNavigate(R.id.action_to_login, null)
        } else {
            onCodeError(code)
        }
    }

    open fun onCodeError(code: String) {
    }

    open fun showToolbar() {
        appCompat().supportActionBar?.show()
    }

    open fun hideToolbar() {
        appCompat().supportActionBar?.hide()
    }

    open fun showLoading() {
        viewModel.isLoading.value = View.VISIBLE
    }

    open fun hideLoading() {
        viewModel.isLoading.value = View.GONE
    }

    open fun setTitle(title: String) {
        appCompat().supportActionBar?.title = title
    }

    open fun appCompat(): MainActivity {
        return activity as MainActivity
    }

    open fun showDialog() {
        appCompat().showLoading()
    }

    open fun hideDialog() {
        appCompat().hideLoading()
    }

    fun clearLogin() {
        appCompat().clearLogin()
    }

    fun handlerLogin() {
        appCompat().handlerLogin()
    }

    fun getUnread() {
        mainViewModel.getUnread()
    }

    val badge: String
        get() = mainViewModel.badge

    fun toError(throwable: Throwable?) {
        appCompat().toError(throwable)
    }

    fun hideBottomNavigation() {
        appCompat().hideBottomNavigation()
    }

    fun showBottomNavigation() {
        appCompat().showBottomNavigation()
    }

    open fun isShowCancel(code: String): Boolean = false

    fun applyDisplayHomeAsUpEnabled(asUpEnable: Boolean) {
        appCompat().supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(asUpEnable)
        }
    }

    fun showMessage(code: String = "", message: String = "") {
        val fragment = BaseDialogFragment.Builder().run {
            withTitle(getString(R.string.error_title))
            withoutCancel()
            withConfirmAction {
                mainViewModel.codeLiveData.setEventValue(code)
            }
            if (isShowCancel(code)) {
                withButtonCancelText(getString(R.string.cancel))
            }
            withDescription(if (code.isEmpty()) message else "$message \n[Code : $code]")
            build()
        }
        fragment.show(childFragmentManager, "dialog")
    }

}
