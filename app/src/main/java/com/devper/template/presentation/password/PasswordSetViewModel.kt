package com.devper.template.presentation.password

import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.auth.GetActionInfoUseCase
import com.devper.template.domain.usecase.auth.SetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
open class PasswordSetViewModel @Inject constructor(
    private val getActionInfoUseCase: GetActionInfoUseCase,
    setPasswordUseCase: SetPasswordUseCase
) : BasePasswordViewModel(setPasswordUseCase) {

    val resultAction = SingleLiveEvent<ResultState<User>>()

    fun getActionInfo() {
        getActionInfoUseCase(actionToken)
            .onEach { resultAction.value = it }
            .launchIn(viewModelScope)
    }
}