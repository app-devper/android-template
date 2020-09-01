package com.devper.template.presentation.password

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.data.preference.AppPreference
import com.devper.template.databinding.FragmentPasswordForgotBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.password.viewmodel.PasswordForgotViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PasswordForgotFragment : BaseFragment<FragmentPasswordForgotBinding>(R.layout.fragment_password_forgot) {

    @Inject
    lateinit var perf: AppPreference
    override val viewModel: PasswordForgotViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun onArguments(it: Bundle?) {
        viewModel.setUsername(perf.userId)
    }

    override fun observeLiveData() {

    }

}
