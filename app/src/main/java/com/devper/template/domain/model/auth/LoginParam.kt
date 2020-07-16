package com.devper.template.domain.model.auth

data class LoginParam(
    var username: String = "",
    var password: String = "",
    val channel: String = "jwt"
)