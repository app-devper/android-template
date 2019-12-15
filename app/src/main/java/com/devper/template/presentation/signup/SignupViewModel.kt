package com.devper.template.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.core.model.user.SignupParam
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.user.SignupUseCase
import timber.log.Timber

class SignupViewModel internal constructor(private val signupUseCase: SignupUseCase) : ViewModel() {

    var user = SignupParam()

    var results: MutableLiveData<ResultState<Unit>> = MutableLiveData()

    fun signup() {
        Timber.d("Signup: $user")
        if (user.username.isNullOrEmpty() || user.password.isNullOrEmpty()) {
            return
        }
        signupUseCase.execute(user) {
            onStart { results.value = ResultState.Loading() }
            onComplete { results.value = ResultState.Success(it) }
            onError { results.value = ResultState.Error(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        signupUseCase.unsubscribe()
    }

}