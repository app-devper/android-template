package com.devper.template.presentation.password

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentPasswordChangeBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordChangeFragment : BaseFragment<FragmentPasswordChangeBinding>(R.layout.fragment_password_change) {

    override val viewModel: PasswordChangeViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.resultVerifyPassword.observe(viewLifecycleOwner, Observer {
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
                    toError(it.throwable)
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
                    viewModel.popBackStack()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable)
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {}

}
