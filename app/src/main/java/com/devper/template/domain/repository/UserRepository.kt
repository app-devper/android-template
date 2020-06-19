package com.devper.template.domain.repository

import com.devper.template.domain.model.user.LoginParam
import com.devper.template.domain.model.user.SignupParam
import com.devper.template.domain.model.user.User

interface UserRepository {

    suspend fun login(request: LoginParam)

    suspend fun signUp(request: SignupParam)

    suspend fun getProfile(): User

    suspend fun getCurrentUser(): User?

    suspend fun clearUser()

}