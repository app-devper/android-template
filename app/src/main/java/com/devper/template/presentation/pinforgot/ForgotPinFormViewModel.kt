package com.devper.template.presentation.pinforgot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.SetPinParam
import com.devper.template.domain.usecase.auth.SetPinUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgotPinFormViewModel @Inject constructor(
    private val setPinUseCase: SetPinUseCase
) : BaseViewModel() {

    private val _actionToken = MutableLiveData<String>()
    var actionToken: String
        get() = _actionToken.value ?: ""
        set(value) {
            _actionToken.value = value
        }

    var resultSetPin = SingleLiveEvent<ResultState<Unit>>()

    fun setPin(pin: String) {
        setPinUseCase(SetPinParam(actionToken, pin))
            .onEach { resultSetPin.value = it }
            .launchIn(viewModelScope)
    }

    fun nextPage() {
        onNavigate(R.id.forgot_pin_form_to_home, null)
    }

}