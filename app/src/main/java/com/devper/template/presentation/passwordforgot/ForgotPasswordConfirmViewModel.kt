package com.devper.template.presentation.passwordforgot

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.AppConfig.FLOW_SET_PASSWORD
import com.devper.template.R
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordConfirmViewModel @Inject constructor() : BaseViewModel() {

    val username = MutableLiveData("")

    fun nextToOtpSetPassword() {
        if (username.value.isNullOrEmpty()) {
            return
        }
        val bundle = bundleOf(
            EXTRA_PARAM to username.value,
            EXTRA_FLOW to FLOW_SET_PASSWORD
        )
        onNavigate(R.id.forgot_password_to_otp_channel, bundle)
    }
}