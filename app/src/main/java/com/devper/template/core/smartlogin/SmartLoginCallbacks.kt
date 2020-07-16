package com.devper.template.core.smartlogin

import com.devper.template.core.smartlogin.users.SmartUser
import com.devper.template.core.smartlogin.util.SmartLoginException

interface SmartLoginCallbacks {

    fun onLoginSuccess(user: SmartUser)

    fun onLoginFailure(e: SmartLoginException)
}
