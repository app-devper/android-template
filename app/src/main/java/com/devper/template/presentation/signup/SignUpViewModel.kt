package com.devper.template.presentation.signup

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.user.SignUpUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {

    var user = MutableLiveData(SignUpParam(username = "worawit.bn@hotmail.com"))

    var results = SingleLiveEvent<ResultState<Unit>>()

    fun signUp() {
        user.value?.let { user ->
            signUpUseCase(user).onEach { results.value = it }.launchIn(viewModelScope)
        }
    }

    fun nextToOtpSetPassword() {
        val bundle = bundleOf(
            AppConfig.EXTRA_PARAM to user.value?.username,
            AppConfig.EXTRA_FLOW to AppConfig.FLOW_SET_PASSWORD
        )
        onNavigate(R.id.signup_to_set_password, bundle)
    }

}