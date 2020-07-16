package com.devper.template.presentation.password

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.devper.template.R
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.SetPasswordParam
import com.devper.template.domain.usecase.auth.SetPasswordUseCase
import com.devper.template.presentation.BaseViewModel

class PasswordViewModel(
    private val setPasswordUseCase: SetPasswordUseCase
) : BaseViewModel() {

    private val _actionToken = MutableLiveData<String>()
    var actionToken: String
        get() = _actionToken.value ?: ""
        set(value) {
            _actionToken.value = value
        }

    var newPassword: ObservableField<String> = ObservableField("password")
    var confirmPassword: ObservableField<String> = ObservableField("password")

    var resultSetPassword: MutableLiveData<ResultState<Unit>> = MutableLiveData()

    fun setPassword() {
        val param = SetPasswordParam(actionToken, newPassword.get() ?: "")
        setPasswordUseCase.execute(param) {
            onStart { resultSetPassword.value = ResultState.Loading() }
            onComplete { resultSetPassword.value = ResultState.Success(it) }
            onError { resultSetPassword.value = ResultState.Error(it) }
        }
    }

    fun nextPage() {
        onNavigate(R.id.set_password_to_login, null)
    }

    override fun onCleared() {
        super.onCleared()
        setPasswordUseCase.unsubscribe()
    }
}