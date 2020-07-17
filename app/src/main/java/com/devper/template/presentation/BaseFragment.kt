package com.devper.template.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.core.exception.AppException
import com.devper.template.domain.core.ErrorMapper
import com.devper.template.presentation.main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment<Binding : ViewDataBinding>(private val layoutId: Int) : Fragment() {

    lateinit var binding: Binding
    abstract val viewModel: BaseViewModel
    val mainViewModel: MainViewModel by sharedViewModel()

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
        observeLiveData()
        getArgument()
    }

    private fun getArgument() {
        viewModel.flow = arguments?.getString(EXTRA_FLOW)
        onArguments(arguments)
    }

    abstract fun setupView()

    abstract fun observeLiveData()

    abstract fun onArguments(it: Bundle?)

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

    open fun toError(throwable: Throwable?) {
        val appError = ErrorMapper.toAppError(throwable)
        appCompat().showMessageTag(appError.resultCode, appError.getDesc())
    }

    open fun toError(appError: AppException) {
        appCompat().showMessageTag(appError.resultCode, appError.getDesc())
    }

    open fun toAppError(throwable: Throwable?): AppException {
        return ErrorMapper.toAppError(throwable)
    }

    fun hideBottomNavigation() {
        appCompat().hideBottomNavigation()
    }

    fun showBottomNavigation() {
        appCompat().showBottomNavigation()
    }

}
