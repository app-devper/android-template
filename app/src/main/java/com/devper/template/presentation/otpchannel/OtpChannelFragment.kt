package com.devper.template.presentation.otpchannel

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentOtpChannelBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpChannelFragment : BaseFragment<FragmentOtpChannelBinding>(R.layout.fragment_otp_channel) {

    override val viewModel: OtpChannelViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getString(EXTRA_PARAM)
        viewModel.getChannel(param)
    }

    override fun observeLiveData() {
        viewModel.results.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    viewModel.setOtpChannel(it.data)
                }
                is ResultState.Error -> {
                    val throwable = it.throwable.toError()
                    showMessage(throwable.resultCode, throwable.getDesc()) {
                        viewModel.popBackStack()
                    }
                }
            }
        })
    }

}
