package com.devper.smartlogin

import android.app.Activity

import java.util.ArrayList

class SmartLoginConfig(val activity: Activity, val callback: SmartLoginCallbacks) {

    var facebookAppId: String? = null
    var lineChannelId: String? = null
    var facebookPermissions: ArrayList<String>? = null

    companion object {

        val defaultFacebookPermissions: ArrayList<String>
            get() {
                val defaultPermissions = ArrayList<String>()
                defaultPermissions.add("public_profile")
                defaultPermissions.add("email")
                return defaultPermissions
            }
    }

}
