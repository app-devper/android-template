package com.devper.template.presentation.passwordchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PasswordParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.usecase.auth.SetPasswordUseCase
import com.devper.template.domain.usecase.auth.VerifyPasswordUseCase
import com.devper.template.presentation.password.BasePasswordViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel @Inject constructor(
    private val verifyPasswordUseCase: VerifyPasswordUseCase,
    setPasswordUseCase: SetPasswordUseCase
) : BasePasswordViewModel(setPasswordUseCase) {

    var currentPassword = MutableLiveData("password")

    var resultVerifyPassword = SingleLiveEvent<ResultState<Verify>>()

    fun changePassword() {
        verifyPassword(PasswordParam(currentPassword.value ?: ""))
    }

    private fun verifyPassword(param: PasswordParam) {
        verifyPasswordUseCase(param)
            .onEach { resultVerifyPassword.value = it }
            .launchIn(viewModelScope)
    }

}

