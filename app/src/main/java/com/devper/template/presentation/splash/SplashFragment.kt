package com.devper.template.presentation.splash

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentSplashBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModel()

    override fun setupView() {
        hideToolbar()
        hideBottomNavigation()
        viewModel.registerDevice()
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
                    toError(it.throwable)
                }
            }
        })
    }
}