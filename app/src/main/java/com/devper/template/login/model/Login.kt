package com.devper.template.login.model

import com.devper.template.common.model.User

data class Login(
    val user: User,
    val accessToken: String
)

