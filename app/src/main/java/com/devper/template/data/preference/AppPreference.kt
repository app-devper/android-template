package com.devper.template.data.preference

import android.content.Context
import android.content.SharedPreferences

class AppPreference private constructor(context: Context) {

    private val sharedpreferences: SharedPreferences

    var appUuid: String
        get() = sharedpreferences.getString(PREF_APP_UUID, "") ?: ""
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_APP_UUID, uuid)
            editor.apply()
        }

    var deviceUuid: String
        get() = sharedpreferences.getString(PREF_DEVICE_UUID, "") ?: ""
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_DEVICE_UUID, uuid)
            editor.apply()
        }

    var token: String
        get() = sharedpreferences.getString(PREF_TOKEN, "") ?: ""
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_TOKEN, uuid)
            editor.apply()
        }

    var fbToken: String?
        get() = sharedpreferences.getString(PREF_FCM_TOKEN, null)
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_FCM_TOKEN, uuid)
            editor.apply()
        }

    var userId: String
        get() = sharedpreferences.getString(PREF_USER_ID, "") ?: ""
        set(id) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_USER_ID, id)
            editor.apply()
        }

    init {
        sharedpreferences = context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
    }

    companion object {

        private const val PREF_APP = "prefs"
        private const val PREF_APP_UUID = "PREF_APP_UUID"
        private const val PREF_DEVICE_UUID = "PREF_DEVICE_UUID"
        private const val PREF_TOKEN = "PREF_TOKEN"
        private const val PREF_FCM_TOKEN = "PREF_FCM_TOKEN"
        private const val PREF_USER_ID = "PREF_USER_ID"

        private lateinit var instance: AppPreference

        fun init(context: Context): AppPreference {
            instance = AppPreference(context)
            return instance
        }

    }

}
