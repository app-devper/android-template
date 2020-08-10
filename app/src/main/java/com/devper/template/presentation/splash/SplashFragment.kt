package com.devper.template.presentation.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentSplashBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels()

    override fun setupView() {
        hideToolbar()
        hideBottomNavigation()
    }

    override fun observeLiveData() {
        viewModel.resultLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    viewModel.nextPage(it.data)
                }
                is ResultState.Error -> {
                    mainViewModel.error(it.throwable)
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {
        viewModel.registerDevice()
    }
}