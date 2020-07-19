package com.devper.template.presentation.login

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
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

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val clearUserUseCase: ClearUserUseCase
) : BaseViewModel() {

    val username = MutableLiveData<String>("wowit")
    val password =  MutableLiveData<String>("password")

    val resultsLogin: SingleLiveEvent<ResultState<String>> = SingleLiveEvent()
    val login: SingleLiveEvent<LoginType> = SingleLiveEvent()

    fun login() {
        val param = LoginParam(username.value ?: "", password.value ?: "")
        loginUseCase.execute(param) {
            onStart { resultsLogin.value = ResultState.Loading() }
            onComplete { resultsLogin.value = ResultState.Success(it) }
            onError { resultsLogin.value = ResultState.Error(it) }
        }
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
        clearUserUseCase.execute(null) {}
    }

    fun nextToOtpSetPin() {
        val bundle = bundleOf(
            EXTRA_FLOW to FLOW_SET_PIN
        )
        onNavigate(R.id.login_to_otp_channel, bundle)
    }

    fun nextToOtpSetPassword() {
        val bundle = bundleOf(
            EXTRA_PARAM to username.value,
            EXTRA_FLOW to FLOW_SET_PASSWORD
        )
        onNavigate(R.id.login_to_forgot_password, bundle)
    }

    fun nextToSignUp() {
        onNavigate(R.id.login_to_signup, null)
    }

    override fun onCleared() {
        super.onCleared()
        loginUseCase.unsubscribe()
        clearUserUseCase.unsubscribe()
    }
}