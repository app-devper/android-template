package com.devper.template.presentation.setting

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.data.preference.AppPreference
import com.devper.template.databinding.FragmentSettingBinding
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    @Inject
    lateinit var perf: AppPreference

    override val viewModel: SettingViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        showBottomNavigation()
        binding.viewModel = viewModel
        if (perf.isSetPin) {
            binding.tvBiometricStatus.text = "ปิด"
        } else {
            binding.tvBiometricStatus.text = "เปิด"
        }
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
