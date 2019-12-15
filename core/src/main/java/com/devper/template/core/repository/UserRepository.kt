package com.devper.template.core.repository

import com.devper.template.core.model.user.LoginParam
import com.devper.template.core.model.user.SignupParam
import com.devper.template.core.model.user.User

interface UserRepository {

    suspend fun login(request: LoginParam): User

    suspend fun signup(request: SignupParam)

    suspend fun getProfile(id: String): User

    suspend fun getCurrentUser(): User?

    suspend fun clearUser()

}