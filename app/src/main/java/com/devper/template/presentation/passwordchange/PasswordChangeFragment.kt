package com.devper.template.presentation.passwordchange

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.devper.template.R
import com.devper.template.databinding.FragmentChangePasswordBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.password.PasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordChangeFragment : BaseFragment<FragmentChangePasswordBinding>(R.layout.fragment_change_password) {

    override val viewModel: PasswordChangeViewModel by viewModel()
    private val passwordViewModel: PasswordViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
        binding.passwordViewModel = passwordViewModel
    }

    override fun observeLiveData() {
        viewModel.resultVerifyPassword.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    passwordViewModel.actionToken = it.data.actionToken
                    passwordViewModel.setPassword()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable)
                }
            }
        })

        passwordViewModel.resultSetPassword.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    findNavController().popBackStack()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable)
                }
            }
        })
    }

}
