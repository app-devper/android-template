package com.devper.template.presentation.signup

import androidx.lifecycle.MutableLiveData
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.user.SignUpUseCase
import com.devper.template.presentation.BaseViewModel
import timber.log.Timber

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : BaseViewModel() {

    var user = MutableLiveData<SignUpParam>(SignUpParam())

    var results = SingleLiveEvent<ResultState<Unit>>()

    fun signUp() {
        user.value?.let { user ->
            Timber.d("SignUp: $user")
            if (user.username.isEmpty() || user.password.isEmpty()) {
                return
            }
            signUpUseCase.execute(user) {
                onStart { results.value = ResultState.Loading() }
                onComplete { results.value = ResultState.Success(it) }
                onError { results.value = ResultState.Error(it) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        signUpUseCase.unsubscribe()
    }

}