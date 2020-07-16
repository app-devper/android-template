package com.devper.template.domain.repository

import com.devper.template.domain.model.auth.*

interface AuthRepository {

    suspend fun login(param: LoginParam): String

    suspend fun setPassword(param: SetPasswordParam)

    suspend fun verifyPassword(param: PasswordParam): Verify

    suspend fun loginPin(param: LoginPinParam): String

    suspend fun verifyPin(param: PinParam): Verify

    suspend fun setPin(param: SetPinParam)
}