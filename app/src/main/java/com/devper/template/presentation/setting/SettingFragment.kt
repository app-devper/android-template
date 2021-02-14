package com.devper.template.presentation.setting

import android.os.Bundle
import androidx.biometric.BiometricManager
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.core.extension.toVisible
import com.devper.template.databinding.FragmentSettingBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override val viewModel: SettingViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel

        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                binding.layoutBiometric.toVisible()
                Timber.d("App can authenticate using biometrics.")
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Timber.e("No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Timber.e("Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Timber.e("The user hasn't associated any biometric credentials with their account.")
        }
    }

    override fun observeLiveData() {
        mainViewModel.profileLiveData.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    binding.user = it.data
                    hideLoading()
                }
                is ResultState.Error -> {}
            }
        })
    }

    override fun onArguments(it: Bundle?) {
        if(!mainViewModel.isLogin()){
            viewModel.onNavigate(R.id.action_to_login_pin, null)
        }
    }

}
