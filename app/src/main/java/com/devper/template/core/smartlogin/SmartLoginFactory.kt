package com.devper.template.core.smartlogin

import com.devper.smartlogin.FacebookLogin

object SmartLoginFactory {
    fun build(loginType: LoginType): SmartLogin {
        return when (loginType) {
            LoginType.Facebook -> FacebookLogin()
            LoginType.Line -> LineLogin()
            LoginType.Google -> GoogleLogin()
            else ->
                FacebookLogin()
        }
    }
}
