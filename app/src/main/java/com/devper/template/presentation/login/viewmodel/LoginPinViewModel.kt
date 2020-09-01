package com.devper.template.presentation.login.viewmodel

import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.LoginPinParam
import com.devper.template.domain.usecase.auth.LoginPinUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginPinViewModel @ViewModelInject constructor(
    private val loginPinUseCase: LoginPinUseCase
) : BaseViewModel() {

    val username = MutableLiveData<String>()

    var resultLoginPin: SingleLiveEvent<ResultState<String>> = SingleLiveEvent()

    fun loginPin(pin: String) {
        loginPinUseCase(LoginPinParam(username.value ?: "", pin))
            .onEach { resultLoginPin.value = it }
            .launchIn(viewModelScope)
    }

    fun setUsername(it: String) {
        username.value = it
    }

    fun nextHome() {
        onNavigate(R.id.pin_code_to_home, null)
    }

    fun nextToForgotPin() {
        val bundle = bundleOf(
            EXTRA_PARAM to username.value
        )
        onNavigate(R.id.pin_code_to_forgot_pin, bundle)
    }

}