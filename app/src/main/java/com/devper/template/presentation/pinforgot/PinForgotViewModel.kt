package com.devper.template.presentation.pinforgot

import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.AppConfig.FLOW_SET_PASSWORD
import com.devper.template.AppConfig.FLOW_SET_PIN
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.LoginParam
import com.devper.template.domain.usecase.auth.LoginUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PinForgotViewModel @ViewModelInject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val username = MutableLiveData("wowit")
    val password = MutableLiveData("password")

    val resultsLogin = SingleLiveEvent<ResultState<Unit>>()

    fun login() {
        val param = LoginParam(username.value, password.value)
        loginUseCase(param)
            .onEach { resultsLogin.value = it }
            .launchIn(viewModelScope)
    }

    fun nextToOtpSetPin() {
        val bundle = bundleOf(
            EXTRA_FLOW to FLOW_SET_PIN
        )
        onNavigate(R.id.forgot_pin_to_otp_channel, bundle)
    }

    fun nextToForgotPassword() {
        val bundle = bundleOf(
            EXTRA_PARAM to username.value,
            EXTRA_FLOW to FLOW_SET_PASSWORD
        )
        onNavigate(R.id.action_to_forgot_password, bundle)
    }
}