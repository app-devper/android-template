package com.devper.template.domain.model.auth

data class SetPasswordParam(
    var actionToken: String = "",
    var password: String = "",
    var confirmPassword: String = ""
) {
    fun isEqual() = password == confirmPassword
    fun isPass() = isEqual() && password.length >= 8 && confirmPassword.length >= 8
}