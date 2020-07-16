package com.devper.template.presentation.otpchannel

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.devper.template.R
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.success
import com.devper.template.domain.model.otp.OtpChannel
import com.devper.template.domain.model.otp.VerifyUserParam
import com.devper.template.domain.usecase.otp.GetChannelUseCase
import com.devper.template.presentation.BaseViewModel

class OtpChannelViewModel(private val getChannelUseCase: GetChannelUseCase) : BaseViewModel() {

    val adapter by lazy { OtpChannelAdapter() }
    var results: MutableLiveData<ResultState<OtpChannel>> = MutableLiveData()

    private val userRefId: String
        get() = results.value?.success()?.userRefId ?: ""

    fun getChannel(param: String?) {
        getChannelUseCase.execute(param) {
            onStart { results.value = ResultState.Loading() }
            onComplete { results.value = ResultState.Success(it) }
            onError { results.value = ResultState.Error(it) }
        }
    }

    fun setOtpChannel(otpChannel: OtpChannel) {
        adapter.submitList(otpChannel.channels)
    }

    fun nextOtpVerify() {
        val channel = adapter.getSelected()
        val bundle = bundleOf(
            "param" to VerifyUserParam(userRefId, channel.channel),
            "flow" to flow
        )
        onNavigate(R.id.otp_channel_to_otp_verify, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        getChannelUseCase.unsubscribe()
    }

}