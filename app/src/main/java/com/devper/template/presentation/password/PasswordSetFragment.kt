package com.devper.template.presentation.password

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentPasswordFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordSetFragment : BaseFragment<FragmentPasswordFormBinding>(R.layout.fragment_password_form) {

    override val viewModel: PasswordSetViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getString(EXTRA_PARAM)
        param?.let {
            viewModel.actionToken = it
            viewModel.getActionInfo()
        }
    }

    override fun observeLiveData() {
        viewModel.resultAction.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    binding.user = it.data
                }
                is ResultState.Error -> {
                    hideLoading()
                    mainViewModel.error(it.throwable)
                }
            }
        })

        viewModel.resultSetPassword.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    viewModel.nextPage()
                }
                is ResultState.Error -> {
                    hideDialog()
                    mainViewModel.error(it.throwable)
                }
            }
        })
    }

}
