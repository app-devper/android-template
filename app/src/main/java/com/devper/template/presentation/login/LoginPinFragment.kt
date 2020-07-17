package com.devper.template.presentation.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentLoginPinBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginPinFragment : BaseFragment<FragmentLoginPinBinding>(R.layout.fragment_login_pin) {

    override val viewModel: LoginPinViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
        binding.pinCodeRoundView.onSuccess = {
            viewModel.loginPin("", it)
        }
        binding.pinCodeKeyboardView.onClick = {
            binding.pinCodeRoundView.setPin(it)
        }
    }

    override fun onArguments(it: Bundle?) {}

    override fun observeLiveData() {
        viewModel.resultLoginPin.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    mainViewModel.setAccessToken(it.data)
                    viewModel.nextHome()
                }
                is ResultState.Error -> {
                    hideDialog()
                    binding.pinCodeRoundView.clearPin()
                    toError(it.throwable)
                }
            }
        })
    }


}
