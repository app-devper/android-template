package com.devper.template.presentation.user

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.databinding.FragmentUserDetailBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding>(R.layout.fragment_user_detail) {

    override val viewModel: UserDetailViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.userResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    viewModel.setUser(it.data)
                }
                is ResultState.Error -> {
                    hideLoading()
                    mainViewModel.error(it.throwable)
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getString(AppConfig.EXTRA_PARAM)
        param?.let{
            viewModel.getUser(it)
        }
    }
}

