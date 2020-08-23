package com.devper.template.presentation.pin

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.data.preference.AppPreference
import com.devper.template.databinding.FragmentPinForgotBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PinForgotFragment : BaseFragment<FragmentPinForgotBinding>(R.layout.fragment_pin_forgot) {

    override val viewModel: PinForgotViewModel by viewModels()

    @Inject
    lateinit var perf: AppPreference

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun onArguments(it: Bundle?) {
        viewModel.setUsername(perf.userId)
    }

    override fun observeLiveData() {
        viewModel.resultsLogin.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    mainViewModel.setAccessToken(it.data)
                    viewModel.nextToOtpSetPin()
                }
                is ResultState.Error -> {
                    hideDialog()
                    mainViewModel.error(it.throwable)
                }
            }
        })
    }

}
