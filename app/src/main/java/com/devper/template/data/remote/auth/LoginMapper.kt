package com.devper.template.data.remote.auth

import com.devper.template.domain.model.auth.*

class LoginMapper {

    fun toRequest(data: LoginParam): LoginRequest {
        return LoginRequest(data.username, data.password, data.channel)
    }

    fun toRequest(data: PasswordParam): PasswordRequest {
        return PasswordRequest(data.password)
    }

    fun toRequest(data: LoginPinParam): LoginPinRequest {
        return LoginPinRequest(data.username, data.pin, "pin")
    }

    fun toRequest(data: PinParam): PinRequest {
        return PinRequest(data.pin)
    }

    fun toRequest(data: SetPasswordParam): PasswordRequest {
        return PasswordRequest(data.password)
    }

    fun toRequest(data: SetPinParam): PinRequest {
        return PinRequest(data.pin)
    }

    fun toDomain(data: VerifyData): Verify {
        return Verify(data.actionToken)
    }
}