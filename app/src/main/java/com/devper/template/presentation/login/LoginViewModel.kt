package com.devper.template.presentation.login

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.AppConfig.FLOW_SET_PASSWORD
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.core.smartlogin.LoginType
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.LoginParam
import com.devper.template.domain.usecase.auth.LoginUseCase
import com.devper.template.domain.usecase.user.ClearUserUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val clearUserUseCase: ClearUserUseCase
) : BaseViewModel() {

    val username = MutableLiveData("wowit")
    val password = MutableLiveData("password")

    val resultsLogin = SingleLiveEvent<ResultState<Unit>>()
    val login: SingleLiveEvent<LoginType> = SingleLiveEvent()

    fun login() {
        val param = LoginParam(username.value, password.value)
        viewModelScope.launch {
            loginUseCase(param).collect {
                resultsLogin.value = it
            }
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
        viewModelScope.launch {
            clearUserUseCase(Unit)
        }
    }

    fun nextToHome() {
        onNavigate(R.id.login_to_home, null)
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