package com.devper.template.core.smartlogin.util

import com.devper.template.core.smartlogin.LoginType

class SmartLoginException : Exception {
    var loginType: LoginType? = null
        private set

    constructor(message: String, loginType: LoginType) : super(message) {
        this.loginType = loginType
    }

    constructor(message: String, cause: Throwable, loginType: LoginType) : super(message, cause) {
        this.loginType = loginType
    }
}
