package com.devper.template.data.preference

import android.content.Context
import android.content.SharedPreferences

class AppPreference constructor(context: Context) {

    private val preferences: SharedPreferences

    val userId: String
        get() = preferences.getString(PREF_USER_ID, "") ?: ""

    val userKey: String
        get() = preferences.getString(PREF_USER_KEY, "") ?: ""

    var pin: String
        get() = preferences.getString(PREF_USER_PIN, "") ?: ""
        set(value) {
            val editor = preferences.edit()
            editor.putString(PREF_USER_PIN, value)
            editor.apply()
        }

    val isSetPin: Boolean
        get() {
            return pin.isNotEmpty() && userId.isNotEmpty()
        }

    fun setUserKey(id: String, key: String) {
        val editor = preferences.edit()
        editor.putString(PREF_USER_ID, id)
        editor.putString(PREF_USER_KEY, key)
        editor.apply()
    }

    fun clear() {
        val editor = preferences.edit()
        editor.putString(PREF_USER_ID, "")
        editor.putString(PREF_USER_KEY, "")
        editor.putString(PREF_USER_PIN, "")
        editor.apply()
    }

    init {
        preferences = context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
    }

    companion object {

        private const val PREF_APP = "prefs"
        private const val PREF_USER_ID = "PREF_USER_ID"
        private const val PREF_USER_KEY = "PREF_USER_KEY"
        private const val PREF_USER_PIN = "PREF_USER_PIN"

    }

}
