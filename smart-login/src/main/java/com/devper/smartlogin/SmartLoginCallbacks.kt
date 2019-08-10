package com.devper.smartlogin

import com.devper.smartlogin.users.SmartUser
import com.devper.smartlogin.util.SmartLoginException

interface SmartLoginCallbacks {

    fun onLoginSuccess(user: SmartUser)

    fun onLoginFailure(e: SmartLoginException)
}
