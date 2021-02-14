package com.devper.template.presentation.passwordforgot

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentPasswordForgotBinding
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordConfirmFragment : BaseFragment<FragmentPasswordForgotBinding>(R.layout.fragment_password_forgot) {

    override val viewModel: ForgotPasswordConfirmViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getString(EXTRA_PARAM)
        param?.let {
            viewModel.username.value = it
        }
    }

    override fun observeLiveData() {}

}
