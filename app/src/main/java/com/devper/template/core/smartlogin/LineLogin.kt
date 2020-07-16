package com.devper.template.core.smartlogin

import android.content.Intent
import android.util.Log
import com.devper.template.core.smartlogin.util.Constants.LINE_LOGIN_REQUEST
import com.devper.template.core.smartlogin.util.SmartLoginException
import com.devper.template.core.smartlogin.util.UserUtil
import com.linecorp.linesdk.LineApiResponseCode
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi

class LineLogin : SmartLogin() {

    override fun login(config: SmartLoginConfig) {
        val activity = config.activity
        config.lineChannelId?.let {
            val params: LineAuthenticationParams =  LineAuthenticationParams.Builder()
                .scopes(arrayListOf(Scope.PROFILE))
                .build()
            val loginIntent = LineLoginApi.getLoginIntent(activity, it,params)
            activity.startActivityForResult(loginIntent, LINE_LOGIN_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, config: SmartLoginConfig) {
        if (requestCode != LINE_LOGIN_REQUEST) {
            return
        }

        val result = LineLoginApi.getLoginResultFromIntent(data)
        when (result.responseCode) {
            LineApiResponseCode.SUCCESS -> {
                // Login successful
                val lineUser = UserUtil.populateLineUser(result)
                config.callback.onLoginSuccess(lineUser)
            }
            LineApiResponseCode.CANCEL -> {
                // Login canceled by user
                Log.e("ERROR", "LINE Login Canceled by user?")
                config.callback.onLoginFailure(SmartLoginException("User cancelled the login line",
                    LoginType.Line
                ))
            }
            else -> {
                // Login canceled due to other error
                Log.e("ERROR", "Login FAILED!")
                Log.e("ERROR", result.errorData.toString())
                config.callback.onLoginFailure(SmartLoginException("Line login failed",
                    LoginType.Line
                ))
            }
        }
    }
}
