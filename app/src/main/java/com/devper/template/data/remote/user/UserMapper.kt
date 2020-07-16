package com.devper.template.data.remote.user

import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.model.user.User

class UserMapper {

    fun toDomain(data: UserData): User {
        return User(data.id, data.username, data.email, data.firstName, data.lastName, data.phone, data.imageUrl)
    }

    fun toRequest(data: SignUpParam): SignUpRequest {
        return SignUpRequest(data.username, data.password, data.email, data.firstName, data.lastName, data.phone)
    }
}