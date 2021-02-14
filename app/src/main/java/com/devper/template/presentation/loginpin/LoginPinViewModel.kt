package com.devper.template.presentation.loginpin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.success
import com.devper.template.domain.model.auth.LoginPinParam
import com.devper.template.domain.usecase.auth.LoginPinUseCase
import com.devper.template.domain.usecase.preference.GetUserIdUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginPinViewModel @Inject constructor(
    private val loginPinUseCase: LoginPinUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : BaseViewModel() {

    val username = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            username.value = getUserIdUseCase(Unit).success()
        }
    }

    var resultLoginPin: SingleLiveEvent<ResultState<Unit>> = SingleLiveEvent()

    fun loginPin(pin: String) {
        loginPinUseCase(LoginPinParam(username.value ?: "", pin))
            .onEach { resultLoginPin.value = it }
            .launchIn(viewModelScope)
    }

    fun nextToHome() {
        onNavigate(R.id.login_pin_to_home, null)
    }

    fun nextToForgotPin() {
        onNavigate(R.id.login_pin_to_forgot_pin, null)
    }

    fun nextToLogin() {
        onNavigate(R.id.login_pin_to_login, null)
    }

}