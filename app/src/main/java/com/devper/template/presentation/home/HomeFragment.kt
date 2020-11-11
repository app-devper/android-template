package com.devper.template.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentHomeBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun setupView() {
    }

    override fun onArguments(it: Bundle?) {
        viewModel.havePin()
    }

    override fun observeLiveData() {
        mainViewModel.profileLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultState.Success -> {
                }
            }
        })

        viewModel.resultPin.observe(viewLifecycleOwner, {
            if (!it) {
                viewModel.nextToPage(mainViewModel.getUser())
            }
        })
    }

}
