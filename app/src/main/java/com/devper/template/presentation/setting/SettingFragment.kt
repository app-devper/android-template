package com.devper.template.presentation.setting

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentSettingBinding
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        showBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        mainViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            binding.user = it
        })
    }

    override fun onArguments(it: Bundle?) {
        mainViewModel.getProfile()
    }

}
