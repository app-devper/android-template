package com.devper.template.presentation.loginpin

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentPinMaxAttemptBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginMaxAttemptFragment : BaseFragment<FragmentPinMaxAttemptBinding>(R.layout.fragment_pin_max_attempt) {

    override val viewModel: BaseViewModel by viewModels()

    override fun setupView() {
        binding.btnForgotPin.setOnClickListener {
            viewModel.onNavigate(R.id.pin_max_to_forgot_pin, null)
        }
    }

    override fun observeLiveData() {

    }

    override fun onArguments(it: Bundle?) {

    }
}