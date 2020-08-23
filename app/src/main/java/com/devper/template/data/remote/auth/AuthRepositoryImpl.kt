package com.devper.template.data.remote.auth

import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.ApiService
import com.devper.template.data.remote.user.UserMapper
import com.devper.template.domain.model.auth.*
import com.devper.template.domain.model.user.User
import com.devper.template.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val pref: AppPreference
) : AuthRepository {

    override suspend fun login(param: LoginParam): String {
        val mapper = LoginMapper()
        return api.login(mapper.toRequest(param)).let { login ->
            pref.setUserKey(param.username, param.password)
            login.accessToken
        }
    }

    override suspend fun setPassword(param: SetPasswordParam) {
        val mapper = LoginMapper()
        return api.setPassword(param.actionToken, mapper.toRequest(param))
    }

    override suspend fun verifyPassword(param: PasswordParam): Verify {
        val mapper = LoginMapper()
        return api.verifyPassword(mapper.toRequest(param)).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun loginPin(param: LoginPinParam): String {
        val mapper = LoginMapper()
        return api.loginPin(mapper.toRequest(param)).accessToken
    }

    override suspend fun verifyPin(param: PinParam): Verify {
        val mapper = LoginMapper()
        return api.verifyPin(mapper.toRequest(param)).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun setPin(param: SetPinParam) {
        val mapper = LoginMapper()
        return api.setPin(param.actionToken, mapper.toRequest(param)).let {
            if (pref.isSetPin) {
                pref.pin = param.pin
            }
        }
    }

    override suspend fun actionInfo(param: String): User {
        val mapper = UserMapper()
        return api.actionInfo(param).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun keepAlive(): String {
        return api.keepAlive().accessToken
    }

}