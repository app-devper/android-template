package com.devper.smartlogin

import android.content.Intent
import com.devper.smartlogin.util.Constants.GOOGLE_LOGIN_REQUEST
import com.devper.smartlogin.util.SmartLoginException
import com.devper.smartlogin.util.UserUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException

class GoogleLogin : SmartLogin() {

    override fun login(config: SmartLoginConfig) {
        val activity = config.activity
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).run {
            requestEmail()
            requestProfile()
            build()
        }
        val signInIntent = GoogleSignIn.getClient(activity, gso)
        activity.startActivityForResult(signInIntent.signInIntent, GOOGLE_LOGIN_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, config: SmartLoginConfig) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        if (requestCode == GOOGLE_LOGIN_REQUEST) {
            try {
                val account = task.getResult(ApiException::class.java)
                val googleUser = UserUtil.populateGoogleUser(account!!)
                config.callback.onLoginSuccess(googleUser)
            } catch (e: ApiException) {
                e.printStackTrace()
                if (e.statusCode == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                    config.callback.onLoginFailure(
                        SmartLoginException(
                            "User cancelled the login google",
                            LoginType.Google
                        )
                    )
                } else {
                    config.callback.onLoginFailure(
                        SmartLoginException(
                            GoogleSignInStatusCodes.getStatusCodeString(e.statusCode),
                            LoginType.Google
                        )
                    )
                }
            }
        } else {
            config.callback.onLoginFailure(SmartLoginException("Google login failed", LoginType.Google))
        }
    }
}
