package com.devper.template.presentation.passwordchange

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PasswordParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.usecase.auth.VerifyPasswordUseCase
import com.devper.template.presentation.BaseViewModel

class PasswordChangeViewModel(
    private val verifyPasswordUseCase: VerifyPasswordUseCase
) : BaseViewModel() {

    var currentPassword: ObservableField<String> = ObservableField("password")

    var resultVerifyPassword: MutableLiveData<ResultState<Verify>> = MutableLiveData()

    fun changePassword() {
        verifyPassword(PasswordParam(currentPassword.get() ?: ""))
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

