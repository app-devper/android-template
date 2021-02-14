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
import com.devper.template.R
import com.devper.template.core.exception.AppException

abstract class BaseFragment<Binding : ViewDataBinding>(private val layoutId: Int) : Fragment() {

    private var dialog: BaseDialogFragment? = null
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
                findNavController().navigate(id, bundle)
            }
            retry = {
                getArgument()
            }
            popBackStack = {
                findNavController().popBackStack()
            }
        }
        setupView()
        observeLiveData()
        getArgument()
    }

    private fun getArgument() {
        viewModel.flow = arguments?.getString(EXTRA_FLOW) ?: ""
        onArguments(arguments)
    }

    abstract fun setupView()

    abstract fun observeLiveData()

    abstract fun onArguments(it: Bundle?)

    private fun handleError(code: String) {
        when (code) {
            "CM-401-107" -> {
                viewModel.onNavigate(R.id.action_to_login_pin, null)
            }
            else -> {
                onDismissError(code)
            }
        }
    }

    open fun onDismissError(code: String) {
    }

    open fun showLoading() {
        viewModel.isLoading.value = View.VISIBLE
    }

    open fun hideLoading() {
        viewModel.isLoading.value = View.GONE
    }

    open fun appCompat(): MainActivity? {
        return if (activity is MainActivity) {
            activity as MainActivity
        } else {
            null
        }
    }

    open fun showDialog() {
        appCompat()?.showLoading()
    }

    open fun hideDialog() {
        appCompat()?.hideLoading()
    }

    fun clearLogin() {
        appCompat()?.clearLogin()
    }

    fun getUnread() {
        mainViewModel.getUnread()
    }

    val badge: Int
        get() = mainViewModel.badge

    fun toError(throwable: AppException) {
        when (throwable.resultCode) {
            "CM-401-112" -> {
                viewModel.onNavigate(R.id.action_to_pin_max_attempt, null)
            }
            "CM-401-105" -> {
                viewModel.onNavigate(R.id.action_to_suspend_account, null)
            }
            else -> {
                showMessage(throwable.resultCode, throwable.getDesc()) {}
            }
        }
    }

    open fun isShowCancel(code: String): Boolean = false

    fun applyDisplayHomeAsUpEnabled(asUpEnable: Boolean) {
        appCompat()?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(asUpEnable)
        }
    }

    fun showMessage(code: String = "", message: String = "", cancel: Boolean = false, confirm: (code: String) -> Unit) {
        dialog?.let {
            if (it.isVisible) {
                return
            }
        }
        dialog = BaseDialogFragment.Builder().run {
            withTitle(getString(R.string.error_title))
            withoutCancel()
            withConfirmAction {
                confirm(code)
            }
            if (cancel) {
                withButtonCancelText(getString(R.string.cancel))
            }
            withDescription(if (code.isEmpty()) message else "$message [Code : $code]")
            build()
        }
        dialog?.show(childFragmentManager, "dialog")
    }

}
