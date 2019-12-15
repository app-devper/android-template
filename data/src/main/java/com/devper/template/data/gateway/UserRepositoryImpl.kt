package com.devper.template.data.gateway

import com.devper.template.core.model.user.LoginParam
import com.devper.template.core.model.user.SignupParam
import com.devper.template.core.model.user.User
import com.devper.template.core.repository.UserRepository
import com.devper.template.data.database.AppDatabase
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.ApiService

class UserRepositoryImpl(
    private val api: ApiService,
    private val db: AppDatabase,
    private val pref: AppPreference
) : UserRepository {

    override suspend fun clearUser() {
        return safeApiCall { db.user().clearUser() }
    }

    override suspend fun getCurrentUser(): User? {
        val mapper = UserMapper()
        return safeApiCall { db.user().getFirstUser() }?.let {
            mapper.toUserDomain(it)
        }
    }

    override suspend fun login(request: LoginParam): User {
        val mapper = UserMapper()
        return safeApiCall { api.login(mapper.toRequest(request)) }.let {
            it.let { login ->
                pref.token = login.accessToken
                db.user().addUser(login.user)
                mapper.toUserDomain(login.user)
            }
        }
    }

    override suspend fun signup(request: SignupParam) {
        val mapper = UserMapper()
        return safeApiCall { api.signup(mapper.toRequest(request)) }.let {
            mapper.toUserDomain(it)
        }
    }

    override suspend fun getProfile(id: String): User {
        val mapper = UserMapper()
        return safeApiCall { api.getUserId(id) }.let {
            mapper.toUserDomain(it)
        }
    }

}