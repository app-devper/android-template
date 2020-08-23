package com.devper.template.presentation.password

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.devper.template.R
import com.devper.template.core.extension.getPasswordScore
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.SetPasswordParam
import com.devper.template.domain.usecase.auth.SetPasswordUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

open class PasswordViewModel(
    private val setPasswordUseCase: SetPasswordUseCase
) : BaseViewModel() {

    var actionToken: String
        get() = password.value?.actionToken ?: ""
        set(value) {
            password.value = password.value?.apply {
                actionToken = value
            }
        }

    val newPassword = MutableLiveData<String>("password")
    val confirmPassword = MutableLiveData<String>("password")
    val password = MutableLiveData<SetPasswordParam>(SetPasswordParam())
    val passwordScore = password.switchMap {
        liveData {
            emit(it.password.getPasswordScore())
        }
    }

    val error = password.switchMap {
        liveData {
            val hide = if (it.isEqual()) View.GONE else View.VISIBLE
            emit(hide)
        }
    }

    val enable = password.switchMap { liveData { emit(it.isPass()) } }

    val resultSetPassword = SingleLiveEvent<ResultState<Unit>>()

    init {
        newPassword.observeForever {
            setNewPassword(it)
        }
        confirmPassword.observeForever {
            setConfirmPassword(it)
        }
    }

    fun setPassword() {
        password.value?.let { param ->
            setPasswordUseCase(param)
                .onEach { resultSetPassword.value = it }
                .launchIn(viewModelScope)
        }
    }

    private fun setNewPassword(new: String) {
        password.value = password.value?.apply {
            password = new
        }
    }

    private fun setConfirmPassword(confirm: String) {
        password.value = password.value?.apply {
            confirmPassword = confirm
        }
    }

    fun nextPage() {
        onNavigate(R.id.set_password_to_login, null)
    }

}