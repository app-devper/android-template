package com.devper.template.presentation.pinchange

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PinParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.usecase.auth.VerifyPinUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangePinConfirmViewModel @Inject constructor(
    private val verifyPinUseCase: VerifyPinUseCase
) : BaseViewModel() {

    var resultVerify = SingleLiveEvent<ResultState<Verify>>()

    fun verifyPin(pin: String) {
        verifyPinUseCase(PinParam(pin))
            .onEach { resultVerify.value = it }
            .launchIn(viewModelScope)
    }

    fun nextToChangePin(actionToken: String) {
        val bundle = bundleOf(
            EXTRA_FLOW to flow,
            EXTRA_PARAM to actionToken
        )
        onNavigate(R.id.change_pin_to_pin_form, bundle)
    }

}