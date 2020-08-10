package com.devper.template.presentation.signup

import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.user.SignUpUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SignUpViewModel @ViewModelInject constructor (
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {

    var user = MutableLiveData<SignUpParam>(SignUpParam(username = "worawit.bn@hotmail.com"))

    var results = SingleLiveEvent<ResultState<Unit>>()

    fun signUp() {
        user.value?.let { user ->
            Timber.d("SignUp: $user")
            if (user.username.isEmpty()) {
                return
            }
            viewModelScope.launch {
                signUpUseCase(user).collect {
                    results.value = it
                }
            }
        }
    }

    fun nextToOtpSetPassword() {
        val bundle = bundleOf(
            AppConfig.EXTRA_PARAM to user.value?.username,
            AppConfig.EXTRA_FLOW to AppConfig.FLOW_SET_PASSWORD
        )
        onNavigate(R.id.signup_to_set_password, bundle)
    }

    override fun onCleared() {
        super.onCleared()
    }

}