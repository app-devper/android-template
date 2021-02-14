package com.devper.template.presentation.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentHomeBinding
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun setupView() {
    }

    override fun onArguments(it: Bundle?) {
        if (!mainViewModel.isLogin()) {
            viewModel.onNavigate(R.id.action_to_login_pin, null)
        }
    }

    override fun observeLiveData() {
        mainViewModel.resultPin.observe(this, {
            if (!it) {
                viewModel.onNavigate(R.id.action_to_pin_verify, null)
            }
        })
    }

}
