package com.devper.template.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class AppPreference constructor(context: Context) : PreferenceStorage {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences: SharedPreferences

    override var userId: String
        get() = preferences.getString(PREF_USER_ID, "") ?: ""
        set(value) {
            val editor = preferences.edit()
            editor.putString(PREF_USER_ID, value)
            editor.apply()
        }

    override var pin: String
        get() = preferences.getString(PREF_USER_PIN, "") ?: ""
        set(value) {
            val editor = preferences.edit()
            editor.putString(PREF_USER_PIN, value)
            editor.apply()
        }

    val isSetPin: Boolean
        get() {
            return pin.isEmpty() && userId.isEmpty()
        }

    override fun clear() {
        val editor = preferences.edit()
        editor.putString(PREF_USER_ID, "")
        editor.putString(PREF_USER_KEY, "")
        editor.putString(PREF_USER_PIN, "")
        editor.apply()
    }

    init {
        preferences = EncryptedSharedPreferences.create(
            context,
            PREF_APP,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val PREF_APP = "prefs"
        private const val PREF_USER_ID = "PREF_USER_ID"
        private const val PREF_USER_KEY = "PREF_USER_KEY"
        private const val PREF_USER_PIN = "PREF_USER_PIN"
    }

}
