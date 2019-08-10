package com.devper.smartlogin

import android.content.Intent

abstract class SmartLogin {

    abstract fun login(config: SmartLoginConfig)

    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, config: SmartLoginConfig)
}
