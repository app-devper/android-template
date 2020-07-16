package com.devper.template.domain.repository

import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.model.user.User

interface UserRepository {

    suspend fun signUp(request: SignUpParam)

    suspend fun getProfile(): User

    suspend fun getCurrentUser(): User

    suspend fun clearUser()

}