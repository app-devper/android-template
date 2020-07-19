package com.devper.template.presentation.user

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentUserFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFormFragment : BaseFragment<FragmentUserFormBinding>(R.layout.fragment_user_form) {

    override val viewModel: UserFormViewModel by viewModel()

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
                    viewModel.popBackStack.invoke()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable)
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
