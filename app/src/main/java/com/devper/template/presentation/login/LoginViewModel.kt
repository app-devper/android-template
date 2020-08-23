package com.devper.template.presentation.login

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
import com.devper.template.core.smartlogin.LoginType
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.LoginParam
import com.devper.template.domain.usecase.auth.LoginUseCase
import com.devper.template.domain.usecase.user.ClearUserUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val loginUseCase: LoginUseCase,
    private val clearUserUseCase: ClearUserUseCase
) : BaseViewModel() {

    val username = MutableLiveData<String>("wowit")
    val password = MutableLiveData<String>("password")

    val resultsLogin = SingleLiveEvent<ResultState<String>>()
    val login: SingleLiveEvent<LoginType> = SingleLiveEvent()

    fun login() {
        val param = LoginParam(username.value ?: "", password.value ?: "")
        loginUseCase(param)
            .onEach { resultsLogin.value = it }
            .launchIn(viewModelScope)
    }

    fun fbLogin() {
        login.value = LoginType.Facebook
    }

    fun lineLogin() {
        login.value = LoginType.Line
    }

    fun googleLogin() {
        login.value = LoginType.Google
    }

    fun clearUser() {
        clearUserUseCase(Unit).launchIn(viewModelScope)
    }

    fun nextToOtpSetPin() {
        val bundle = bundleOf(
            EXTRA_FLOW to FLOW_SET_PIN
        )
        onNavigate(R.id.login_to_otp_channel, bundle)
    }

    fun nextToForgotPassword() {
        val bundle = bundleOf(
            EXTRA_PARAM to username.value
        )
        onNavigate(R.id.login_to_forgot_password, bundle)
    }

    fun nextToSetPassword() {
        val bundle = bundleOf(
            EXTRA_PARAM to username.value,
            EXTRA_FLOW to FLOW_SET_PASSWORD
        )
        onNavigate(R.id.login_to_set_password, bundle)
    }

    fun nextToSignUp() {
        onNavigate(R.id.login_to_signup, null)
    }

}