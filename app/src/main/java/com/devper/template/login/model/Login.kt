package com.devper.template.login.model

import com.devper.template.app.model.User

data class Login(
    val user: User,
    val accessToken: String
)

