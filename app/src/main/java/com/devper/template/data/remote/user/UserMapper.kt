package com.devper.template.data.remote.user

import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.UserUpdateParam
import com.devper.template.domain.model.user.Users

class UserMapper {

    fun toDomain(data: UserData): User {
        return User(data.id, data.role, data.username, data.email, data.firstName, data.lastName, data.phone, data.imageUrl)
    }

    fun toDomain(data: UsersData): Users {
        val users = data.results.map {
            toDomain(it)
        }
        return Users(data.page, users, data.total, data.totalPages)
    }

    fun toRequest(data: SignUpParam): SignUpRequest {
        return SignUpRequest(data.username, data.password, data.email, data.firstName, data.lastName, data.phone)
    }

    fun toRequest(data: UserUpdateParam): UserRequest {
        return UserRequest(data.firstName, data.lastName, data.phone, data.email, data.gender)
    }
}