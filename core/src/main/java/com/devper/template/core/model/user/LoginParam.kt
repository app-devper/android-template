package com.devper.template.core.model.user

data class LoginParam(
    var username: String,
    var password: String
) {
    val channel: String = "jwt"
}