package com.devper.template.presentation.pin

import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PinParam
import com.devper.template.domain.usecase.auth.SavePinUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PinVerifyViewModel @Inject constructor(
    private val savePinUseCase: SavePinUseCase
) : BaseViewModel() {

    var resultVerify = SingleLiveEvent<ResultState<Unit>>()

    fun verifyPin(pin: String) {
        savePinUseCase(PinParam(pin))
            .onEach { resultVerify.value = it }
            .launchIn(viewModelScope)
    }

}