package com.devper.template.presentation.user

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentUserFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFormFragment : BaseFragment<FragmentUserFormBinding>(R.layout.fragment_user_form) {

    override val viewModel: UserFormViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.userResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    mainViewModel.setUser(it.data)
                    viewModel.popBackStack()
                }
                is ResultState.Error -> {
                    hideDialog()
                    mainViewModel.error(it.throwable)
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {
        mainViewModel.getUser()?.let {
            viewModel.setUser(it)
        }
    }
}

