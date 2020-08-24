package com.devper.template.presentation.setting

import android.os.Bundle
import androidx.biometric.BiometricManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.core.extension.toVisible
import com.devper.template.data.preference.AppPreference
import com.devper.template.databinding.FragmentSettingBinding
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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
        mainViewModel.userLiveData.observe(viewLifecycleOwner, {
            binding.user = it
        })
    }

    override fun onArguments(it: Bundle?) {
        mainViewModel.getProfile()
    }

}
