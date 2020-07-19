package com.devper.template.domain.repository

import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.UserUpdateParam
import com.devper.template.domain.model.user.Users

interface UserRepository {

    suspend fun signUp(request: SignUpParam)

    suspend fun getProfile(): User

    suspend fun updateProfile(param: UserUpdateParam): User

    suspend fun updateUser(param: UserUpdateParam): User

    suspend fun getCurrentUser(): User

    suspend fun getUsers(page: Int): Users

    suspend fun getUser(userId: String): User

    suspend fun clearUser()

}