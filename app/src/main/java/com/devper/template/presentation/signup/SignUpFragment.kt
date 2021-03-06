package com.devper.template.presentation.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentSignupBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    override val viewModel: SignUpViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.results.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    viewModel.nextToOtpSetPassword()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable.toError())
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {}

}
