package com.devper.template.presentation.setting

import androidx.core.os.bundleOf
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.FLOW_CHANGE_PASSWORD
import com.devper.template.AppConfig.FLOW_CHANGE_PIN
import com.devper.template.AppConfig.FLOW_UPDATE_PROFILE
import com.devper.template.R
import com.devper.template.presentation.BaseViewModel

class SettingViewModel : BaseViewModel() {

    fun nextChangePassword() {
        val bundle = bundleOf(
            EXTRA_FLOW to FLOW_CHANGE_PASSWORD
        )
        onNavigate(R.id.setting_to_change_password, bundle)
    }

    fun nextChangePin() {
        val bundle = bundleOf(
            EXTRA_FLOW to FLOW_CHANGE_PIN
        )
        onNavigate(R.id.setting_to_change_pin, bundle)
    }

    fun nextToLogin() {
        onNavigate(R.id.setting_to_login, null)
    }

    fun nextToUser() {
        onNavigate(R.id.setting_to_user, null)
    }

    fun nextToUpdateProfile() {
        val bundle = bundleOf(
            EXTRA_FLOW to FLOW_UPDATE_PROFILE
        )
        onNavigate(R.id.setting_to_user_form, bundle)
    }
}