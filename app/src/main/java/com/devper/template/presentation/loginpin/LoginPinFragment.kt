package com.devper.template.presentation.loginpin

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.core.platform.biometric.BiometricController
import com.devper.template.databinding.FragmentLoginPinBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginPinFragment : BaseFragment<FragmentLoginPinBinding>(R.layout.fragment_login_pin), BiometricController.Callback {

    override val viewModel: LoginPinViewModel by viewModels()
    private lateinit var biometric: BiometricController

    override fun setupView() {
        binding.viewModel = viewModel
        binding.pinCodeRoundView.onSuccess = {
            viewModel.loginPin(it)
        }
        binding.pinCodeKeyboardView.apply {
            onClick = {
                binding.pinCodeRoundView.setPin(it)
            }
            onOther = {
                authenticate()
            }
        }

        biometric = BiometricController(requireActivity(), this)
    }

    private fun authenticate() {
        biometric.authenticate()
    }

    override fun onArguments(it: Bundle?) {
        clearLogin()
    }

    override fun observeLiveData() {
        viewModel.username.observe(this, {
            authenticate()
        })
        viewModel.resultLoginPin.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    mainViewModel.initLogin()
                }
                is ResultState.Error -> {
                    hideDialog()
                    binding.pinCodeRoundView.clearPin()
                    toError(it.throwable.toError())
                }
            }
        })
        mainViewModel.loginLiveData.observe(this, {
            if (it) {
                viewModel.nextToHome()
            }
        })
    }

    override fun onAuthenticated() {
        viewModel.loginPin("")
    }

    override fun onError() {
    }

}
