package com.devper.template.presentation.otpverify

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.AppConfig.FLOW_SET_PASSWORD
import com.devper.template.AppConfig.FLOW_SET_PIN
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.core.platform.widget.KeyboardButtonEnum
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.otp.VerifyCode
import com.devper.template.domain.model.otp.VerifyCodeParam
import com.devper.template.domain.model.otp.VerifyUser
import com.devper.template.domain.model.otp.VerifyUserParam
import com.devper.template.domain.usecase.otp.VerifyCodeUseCase
import com.devper.template.domain.usecase.otp.VerifyUserUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OtpVerifyViewModel @Inject constructor(
    private val verifyUserUseCase: VerifyUserUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase
) : BaseViewModel() {

    private val maxCode = 6

    val code = MutableLiveData("")
    private var pin: String
        get() = code.value ?: ""
        set(value) {
            code.value = value
        }

    var resultVerifyUser = SingleLiveEvent<ResultState<VerifyUser>>()
    var resultVerifyCode = SingleLiveEvent<ResultState<VerifyCode>>()

    val verifyUser = MutableLiveData<VerifyUser>()

    fun verifyUser(param: VerifyUserParam) {
        verifyUserUseCase(param)
            .onEach { resultVerifyUser.value = it }
            .launchIn(viewModelScope)
    }

    fun setOtp(value: KeyboardButtonEnum) {
        if (value.buttonValue != -1) {
            if (pin.length == maxCode) {
                return
            }
            pin += value.buttonValue
            if (pin.length == maxCode) {
                verifyCode()
            }
        } else {
            if (pin.isNotEmpty()) {
                pin = pin.substring(0, pin.length - 1)
            }
        }
    }

    fun setVerifyUser(data: VerifyUser) {
        verifyUser.value = data
        clearCode()
    }

    private fun verifyCode() {
        val param = VerifyCodeParam().apply {
            userRefId = verifyUser.value?.userRefId
            refCode = verifyUser.value?.refCode
            code = pin
        }
        verifyCode(param)
    }

    private fun verifyCode(param: VerifyCodeParam) {
        verifyCodeUseCase(param)
            .onEach { resultVerifyCode.value = it }
            .launchIn(viewModelScope)
    }

    fun clearCode() {
        pin = ""
    }

    fun nextPage(verifyCode: VerifyCode) {
        val bundle = bundleOf(
            EXTRA_FLOW to flow,
            EXTRA_PARAM to verifyCode.actionToken
        )
        if (flow == FLOW_SET_PASSWORD) {
            onNavigate(R.id.otp_verify_to_set_password, bundle)
        } else if (flow == FLOW_SET_PIN) {
            onNavigate(R.id.otp_verify_to_pin_form, bundle)
        }
    }

}