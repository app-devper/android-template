package com.devper.template.login.model

import com.devper.template.common.model.BaseResponse
import com.devper.template.common.model.User

data class LoginResponse(val data: Result) : BaseResponse()

data class Result(
    val user: User,
    val accessToken: String
)

