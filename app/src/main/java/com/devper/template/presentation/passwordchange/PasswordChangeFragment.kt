package com.devper.template.presentation.passwordchange

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentPasswordChangeBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordChangeFragment : BaseFragment<FragmentPasswordChangeBinding>(R.layout.fragment_password_change) {

    override val viewModel: PasswordChangeViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.resultVerifyPassword.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    viewModel.actionToken = it.data.actionToken
                    viewModel.setPassword()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable.toError())
                }
            }
        })

        viewModel.resultSetPassword.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    viewModel.popBackStack()
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
