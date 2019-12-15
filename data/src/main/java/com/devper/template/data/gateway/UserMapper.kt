package com.devper.template.data.gateway

import com.devper.template.data.remote.user.LoginRequest
import com.devper.template.data.remote.user.SignupRequest
import com.devper.template.data.remote.user.UserData
import com.devper.template.core.model.user.LoginParam
import com.devper.template.core.model.user.SignupParam
import com.devper.template.core.model.user.User

class UserMapper {

    fun toUserDomain(data: UserData): User {
        return User(data.id, data.username, data.email, data.firstName, data.lastName, data.phone, data.imageUrl)
    }

    fun toRequest(data: LoginParam): LoginRequest {
        return LoginRequest(data.username, data.password, data.channel)
    }

    fun toRequest(data: SignupParam): SignupRequest {
        return SignupRequest(data.username, data.password, data.email, data.firstName, data.lastName, data.phone)
    }
}