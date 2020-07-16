package com.devper.template.presentation.login

import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.devper.template.R
import com.devper.template.core.smartlogin.LoginType
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.model.auth.LoginParam
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.user.ClearUserUseCase
import com.devper.template.domain.usecase.auth.LoginUseCase
import com.devper.template.presentation.BaseViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val clearUserUseCase: ClearUserUseCase
) : BaseViewModel() {

    var username: ObservableField<String> = ObservableField("wowit")
    var password: ObservableField<String> = ObservableField("password")

    var results: MutableLiveData<ResultState<String>> = MutableLiveData()
    var login: SingleLiveEvent<LoginType> = SingleLiveEvent()

    fun login() {
        val param = LoginParam(username.get() ?: "", password.get() ?: "")
        loginUseCase.execute(param) {
            onStart { results.value = ResultState.Loading() }
            onComplete { results.value = ResultState.Success(it) }
            onError { results.value = ResultState.Error(it) }
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

    fun nextToOtp() {
        val bundle = bundleOf(
            "flow" to "set_pin"
        )
        onNavigate(R.id.login_to_otp_channel, bundle)
    }

    fun nextToSignUp() {
        onNavigate(R.id.login_to_signup, null)
    }

    fun nextOtpChannel() {
        val bundle = bundleOf(
            "param" to username.get(),
            "flow" to "set_password"
        )
        onNavigate(R.id.login_to_otp_channel, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        loginUseCase.unsubscribe()
        clearUserUseCase.unsubscribe()
    }
}