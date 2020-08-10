package com.devper.template.presentation.password

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.AppConfig.FLOW_SET_PASSWORD
import com.devper.template.R
import com.devper.template.presentation.BaseViewModel

open class PasswordForgotViewModel : BaseViewModel() {

    val username = MutableLiveData<String>()

    fun nextToOtpSetPassword() {
        val bundle = bundleOf(
            EXTRA_PARAM to username.value,
            EXTRA_FLOW to FLOW_SET_PASSWORD
        )
        onNavigate(R.id.forgot_password_to_otp_channel, bundle)
    }
}