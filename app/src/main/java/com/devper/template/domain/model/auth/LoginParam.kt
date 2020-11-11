package com.devper.template.domain.model.auth

data class LoginParam(
    val username: String? = null,
    val password: String? = null,
    val channel: String = "jwt"
)