package com.devper.template.presentation.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentSuspendAccountBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSuspendFragment : BaseFragment<FragmentSuspendAccountBinding>(R.layout.fragment_suspend_account) {

    override val viewModel: BaseViewModel by viewModels()

    override fun setupView() {
        binding.btnForgotPassword.setOnClickListener {
            viewModel.onNavigate(R.id.suspend_account_to_forgot_password, null)
        }
    }

    override fun onArguments(it: Bundle?) {
    }

    override fun observeLiveData() {}

}
