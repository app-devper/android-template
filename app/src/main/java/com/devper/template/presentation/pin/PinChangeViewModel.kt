package com.devper.template.presentation.pin

import androidx.core.os.bundleOf
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PinParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.usecase.auth.VerifyPinUseCase
import com.devper.template.presentation.BaseViewModel

class PinChangeViewModel(
    private val verifyPinUseCase: VerifyPinUseCase
) : BaseViewModel() {

    var resultVerify = SingleLiveEvent<ResultState<Verify>>()

    fun verifyPin(pin: String) {
        verifyPinUseCase.execute(PinParam(pin)) {
            onStart { resultVerify.value = ResultState.Loading() }
            onComplete { resultVerify.value = ResultState.Success(it) }
            onError { resultVerify.value = ResultState.Error(it) }
        }
    }

    fun nextPage(actionToken: String) {
        val bundle = bundleOf(
            EXTRA_FLOW to flow,
            EXTRA_PARAM to actionToken
        )
        onNavigate(R.id.change_pin_to_pin_form, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        verifyPinUseCase.unsubscribe()
    }
}