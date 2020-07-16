package com.devper.template.core.smartlogin

import android.app.Activity
import com.devper.template.core.smartlogin.SmartLoginCallbacks

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
