package com.devper.template.presentation.userupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.databinding.FragmentUserFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.domain.model.user.User
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserUpdateFragment : BaseFragment<FragmentUserFormBinding>(R.layout.fragment_user_form) {

    override val viewModel: UserFormViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.resultUser.observe(viewLifecycleOwner, {
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

    override fun onArguments(it: Bundle?) {
        val param = it?.getParcelable<User>(AppConfig.EXTRA_PARAM)
        param?.let {
            viewModel.setUser(it)
        }
    }
}

