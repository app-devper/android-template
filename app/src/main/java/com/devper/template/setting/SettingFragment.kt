package com.devper.template.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.devper.template.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey)
    }
}
