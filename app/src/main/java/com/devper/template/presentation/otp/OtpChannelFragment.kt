package com.devper.template.presentation.otp

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentOtpChannelBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpChannelFragment : BaseFragment<FragmentOtpChannelBinding>(R.layout.fragment_otp_channel) {

    override val viewModel: OtpChannelViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getString(EXTRA_PARAM)
        viewModel.getChannel(param)
    }

    override fun observeLiveData() {
        viewModel.results.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    viewModel.setOtpChannel(it.data)
                }
                is ResultState.Error -> {
                    hideLoading()
                    toError(it.throwable)
                }
            }
        })
    }

}
