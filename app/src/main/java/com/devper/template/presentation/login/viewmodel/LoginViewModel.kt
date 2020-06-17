package com.devper.template.presentation.login.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.smartlogin.LoginType
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.LoginParam
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.user.ClearUserUseCase
import com.devper.template.domain.usecase.user.LoginUseCase

class LoginViewModel internal constructor(
    private val loginUseCase: LoginUseCase,
    private val clearUserUseCase: ClearUserUseCase
) : ViewModel() {

    var username: ObservableField<String> = ObservableField("wowit")
    var password: ObservableField<String> = ObservableField("password")

    var results: MutableLiveData<ResultState<User>> = MutableLiveData()
    var login: MutableLiveData<LoginType> = MutableLiveData()

    fun login() {
        if (username.get().isNullOrEmpty() || password.get().isNullOrEmpty()) return
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
        clearUserUseCase.execute(null) {
            onError {
                it.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loginUseCase.unsubscribe()
        clearUserUseCase.unsubscribe()
    }
}