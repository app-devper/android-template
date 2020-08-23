package com.devper.template.presentation.password

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.auth.GetActionInfoUseCase
import com.devper.template.domain.usecase.auth.SetPasswordUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class PasswordSetViewModel @ViewModelInject constructor(
    private val getActionInfoUseCase: GetActionInfoUseCase,
    setPasswordUseCase: SetPasswordUseCase
) : PasswordViewModel(setPasswordUseCase) {

    val resultAction = SingleLiveEvent<ResultState<User>>()

    fun getActionInfo() {
        getActionInfoUseCase(actionToken)
            .onEach { resultAction.value = it }
            .launchIn(viewModelScope)
    }
}