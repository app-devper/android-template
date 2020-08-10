package com.devper.template.presentation.password

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PasswordParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.usecase.auth.SetPasswordUseCase
import com.devper.template.domain.usecase.auth.VerifyPasswordUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PasswordChangeViewModel @ViewModelInject constructor(
    private val verifyPasswordUseCase: VerifyPasswordUseCase,
    setPasswordUseCase: SetPasswordUseCase
) : PasswordViewModel(setPasswordUseCase) {

    var currentPassword = MutableLiveData<String>("password")

    var resultVerifyPassword = SingleLiveEvent<ResultState<Verify>>()

    fun changePassword() {
        verifyPassword(PasswordParam(currentPassword.value ?: ""))
    }

    private fun verifyPassword(param: PasswordParam) {
        viewModelScope.launch {
            verifyPasswordUseCase(param).collect {
                resultVerifyPassword.value = it
            }
        }
    }

}

