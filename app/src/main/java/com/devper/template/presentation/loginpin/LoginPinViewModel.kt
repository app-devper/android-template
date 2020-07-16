package com.devper.template.presentation.loginpin

import androidx.lifecycle.MutableLiveData
import com.devper.template.R
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.LoginPinParam
import com.devper.template.domain.usecase.auth.LoginPinUseCase
import com.devper.template.presentation.BaseViewModel

class LoginPinViewModel(
    private val loginPinUseCase: LoginPinUseCase
) : BaseViewModel() {

    var resultLoginPin: MutableLiveData<ResultState<String>> = MutableLiveData()

    fun loginPin(username: String, pin: String) {
        loginPinUseCase.execute(LoginPinParam(username, pin)) {
            onStart { resultLoginPin.value = ResultState.Loading() }
            onComplete { resultLoginPin.value = ResultState.Success(it) }
            onError { resultLoginPin.value = ResultState.Error(it) }
        }
    }

    fun nextHome() {
        onNavigate(R.id.pin_code_to_home, null)
    }

    fun nextToLogin() {
        onNavigate(R.id.pin_code_to_login, null)
    }

    override fun onCleared() {
        super.onCleared()
        loginPinUseCase.unsubscribe()
    }

}