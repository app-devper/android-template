package com.devper.template.presentation.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.data.preference.AppPreference
import com.devper.template.databinding.FragmentLoginPinBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginPinFragment : BaseFragment<FragmentLoginPinBinding>(R.layout.fragment_login_pin) {

    @Inject lateinit var perf: AppPreference
    override val viewModel: LoginPinViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
        binding.pinCodeRoundView.onSuccess = {
            viewModel.loginPin(it)
        }
        binding.pinCodeKeyboardView.onClick = {
            binding.pinCodeRoundView.setPin(it)
        }
    }

    override fun onArguments(it: Bundle?) {
        viewModel.setUsername(perf.userId)
        clearLogin()
    }

    override fun observeLiveData() {
        viewModel.resultLoginPin.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    mainViewModel.setAccessToken(it.data)
                    handlerLogin()
                    viewModel.nextHome()
                }
                is ResultState.Error -> {
                    hideDialog()
                    binding.pinCodeRoundView.clearPin()
                    mainViewModel.error(it.throwable)
                }
            }
        })
    }

    override fun onCodeError(code: String) {
        if (code == "CM-401-112") {
            viewModel.nextToForgotPin()
        }
    }

    override fun isShowCancel(code: String): Boolean {
        if (code == "CM-401-112") {
            return true
        }
        return super.isShowCancel(code)
    }

}
