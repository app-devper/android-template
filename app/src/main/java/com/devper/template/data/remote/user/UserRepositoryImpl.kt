package com.devper.template.data.remote.user

import com.devper.template.data.database.AppDatabase
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.ApiService
import com.devper.template.domain.model.user.LoginParam
import com.devper.template.domain.model.user.SignupParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: ApiService,
    private val db: AppDatabase,
    private val pref: AppPreference
) : UserRepository {

    override suspend fun clearUser() {
        return db.user().clearUser()
    }

    override suspend fun getCurrentUser(): User? {
        val mapper = UserMapper()
        return db.user().getFirstUser()?.let {
            mapper.toUserDomain(it)
        }
    }

    override suspend fun login(request: LoginParam) {
        val mapper = UserMapper()
        return api.login(mapper.toRequest(request)).let { login ->
            pref.token = login.accessToken
        }
    }

    override suspend fun signUp(request: SignupParam) {
        val mapper = UserMapper()
        return api.signup(mapper.toRequest(request)).let {
            mapper.toUserDomain(it)
        }
    }

    override suspend fun getProfile(): User {
        val mapper = UserMapper()
        return api.getProfile().let {
            db.user().addUser(it)
            mapper.toUserDomain(it)
        }
    }

}