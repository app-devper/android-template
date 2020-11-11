package com.devper.template.presentation.pin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.FLOW_CHANGE_PIN
import com.devper.template.AppConfig.FLOW_SET_PIN
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.SetPinParam
import com.devper.template.domain.usecase.auth.SetPinUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PinFormViewModel @ViewModelInject constructor(
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
        if (flow == FLOW_CHANGE_PIN) {
            popBackStack()
        } else if (flow == FLOW_SET_PIN) {
            onNavigate(R.id.pin_form_to_home, null)
        }
    }

}