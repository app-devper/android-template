package com.devper.template.data.remote.auth

import com.devper.template.domain.model.auth.*
import com.devper.template.domain.model.user.User

interface AuthRepository {

    suspend fun login(param: LoginParam): String

    suspend fun setPassword(param: SetPasswordParam)

    suspend fun verifyPassword(param: PasswordParam): Verify

    suspend fun loginPin(param: LoginPinParam): String

    suspend fun verifyPin(param: PinParam): Verify

    suspend fun setPin(param: SetPinParam)

    suspend fun actionInfo(param: String): User

    suspend fun keepAlive(): String

}