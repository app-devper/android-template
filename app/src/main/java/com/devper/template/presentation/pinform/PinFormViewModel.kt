package com.devper.template.presentation.pinform

import androidx.lifecycle.MutableLiveData
import com.devper.template.R
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.SetPinParam
import com.devper.template.domain.usecase.auth.SetPinUseCase
import com.devper.template.presentation.BaseViewModel

class PinFormViewModel(
    private val setPinUseCase: SetPinUseCase
) : BaseViewModel() {

    private val _actionToken = MutableLiveData<String>()
    var actionToken: String
        get() = _actionToken.value ?: ""
        set(value) {
            _actionToken.value = value
        }

    var resultSetPin: MutableLiveData<ResultState<Boolean>> = MutableLiveData()

    fun setPin(pin: String) {
        setPinUseCase.execute(SetPinParam(actionToken, pin)) {
            onStart { resultSetPin.value = ResultState.Loading() }
            onComplete { resultSetPin.value = ResultState.Success(true) }
            onError { resultSetPin.value = ResultState.Error(it) }
        }
    }

    fun nextToHome() {
        onNavigate(R.id.pin_form_to_home, null)
    }

    override fun onCleared() {
        super.onCleared()
        setPinUseCase.unsubscribe()
    }

}