package com.devper.template.presentation.setting

import androidx.core.os.bundleOf
import com.devper.template.R
import com.devper.template.presentation.BaseViewModel

class SettingViewModel : BaseViewModel() {

    fun nextChangePassword() {
        val bundle = bundleOf(
            "flow" to "change_password"
        )
        onNavigate(R.id.setting_to_change_password, bundle)
    }

    fun nextChangePin() {
        val bundle = bundleOf(
            "flow" to "change_pin"
        )
        onNavigate(R.id.setting_to_change_pin, bundle)
    }

    fun nextToLogin() {
        onNavigate(R.id.setting_to_login, null)
    }
}