package com.devper.template.presentation.otp

import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.success
import com.devper.template.domain.model.otp.OtpChannel
import com.devper.template.domain.model.otp.VerifyUserParam
import com.devper.template.domain.usecase.otp.GetChannelUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OtpChannelViewModel @ViewModelInject constructor(
    private val getChannelUseCase: GetChannelUseCase
) : BaseViewModel() {

    val adapter by lazy { OtpChannelAdapter() }
    var results = SingleLiveEvent<ResultState<OtpChannel>>()

    private val userRefId: String
        get() = results.value?.success()?.userRefId ?: ""

    fun getChannel(param: String?) {
        viewModelScope.launch {
            getChannelUseCase(param).collect {
                results.value = it
            }
        }
    }

    fun setOtpChannel(otpChannel: OtpChannel) {
        adapter.submitList(otpChannel.channels)
    }

    fun nextOtpVerify() {
        val channel = adapter.getSelected()
        val bundle = bundleOf(
            EXTRA_FLOW to flow,
            EXTRA_PARAM to VerifyUserParam(userRefId, channel.channel)
        )
        onNavigate(R.id.otp_channel_to_otp_verify, bundle)
    }

}