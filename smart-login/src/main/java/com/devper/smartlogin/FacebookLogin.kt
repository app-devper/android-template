package com.devper.smartlogin

import android.content.Intent
import android.util.Log

import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.devper.smartlogin.util.SmartLoginException
import com.devper.smartlogin.util.UserUtil
import java.util.*

class FacebookLogin internal constructor() : SmartLogin() {

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun login(config: SmartLoginConfig) {
        val activity = config.activity
        val callback = config.callback
        var permissions: ArrayList<String>? = config.facebookPermissions
        if (permissions == null) {
            permissions = SmartLoginConfig.defaultFacebookPermissions
        }
        LoginManager.getInstance().logInWithReadPermissions(activity, permissions)
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val facebookUser = UserUtil.populateFacebookUser(loginResult.accessToken)
                Profile.fetchProfileForCurrentAccessToken()
                if (Profile.getCurrentProfile() != null) {
                    facebookUser.firstName = Profile.getCurrentProfile().firstName
                    facebookUser.lastName = Profile.getCurrentProfile().lastName
                    facebookUser.profileLink = Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString()
                    callback.onLoginSuccess(facebookUser)
                } else {
                    val profileTracker = object : ProfileTracker() {
                        override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile) {
                            this.stopTracking()
                            Profile.setCurrentProfile(currentProfile)
                            facebookUser.firstName = Profile.getCurrentProfile().firstName
                            facebookUser.lastName = Profile.getCurrentProfile().lastName
                            facebookUser.profileLink = Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString()
                            callback.onLoginSuccess(facebookUser)
                        }
                    }
                    profileTracker.startTracking()
                }
            }

            override fun onCancel() {
                Log.d("Facebook Login", "User cancelled the login process")
                callback.onLoginFailure(SmartLoginException("User cancelled the login facebook", LoginType.Facebook))
            }

            override fun onError(e: FacebookException) {
                e.printStackTrace()
                callback.onLoginFailure(SmartLoginException(e.localizedMessage, e, LoginType.Facebook))
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, config: SmartLoginConfig) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
