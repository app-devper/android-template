package com.devper.template.presentation.password

import androidx.lifecycle.MutableLiveData
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PasswordParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.usecase.auth.SetPasswordUseCase
import com.devper.template.domain.usecase.auth.VerifyPasswordUseCase

class PasswordChangeViewModel(
    setPasswordUseCase: SetPasswordUseCase,
    private val verifyPasswordUseCase: VerifyPasswordUseCase
) : PasswordViewModel(setPasswordUseCase) {

    var currentPassword = MutableLiveData<String>("password")

    var resultVerifyPassword = SingleLiveEvent<ResultState<Verify>>()

    fun changePassword() {
        verifyPassword(PasswordParam(currentPassword.value ?: ""))
    }

    private fun verifyPassword(param: PasswordParam) {
        verifyPasswordUseCase.execute(param) {
            onStart { resultVerifyPassword.value = ResultState.Loading() }
            onComplete { resultVerifyPassword.value = ResultState.Success(it) }
            onError { resultVerifyPassword.value = ResultState.Error(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        verifyPasswordUseCase.unsubscribe()
    }
}

