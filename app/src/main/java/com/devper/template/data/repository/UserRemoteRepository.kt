package com.devper.template.data.repository

import com.devper.template.data.database.AppDatabase
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.AppService
import com.devper.template.domain.model.user.LoginParam
import com.devper.template.domain.model.user.SignupParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.repository.UserRepository

class UserRemoteRepository(
    private val api: AppService,
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

    override suspend fun login(request: LoginParam): User {
        val mapper = UserMapper()
        return api.login(mapper.toRequest(request)).let {
            it.data.let { login ->
                pref.token = login.accessToken
                db.user().addUser(login.user)
                mapper.toUserDomain(login.user)
            }
        }
    }

    override suspend fun signup(request: SignupParam) {
        val mapper = UserMapper()
        return api.signup(mapper.toRequest(request)).let {
            mapper.toUserDomain(it.data)
        }
    }

    override suspend fun getProfile(id: String): User {
        val mapper = UserMapper()
        return api.getUserId(id).let {
            mapper.toUserDomain(it.data)
        }
    }

}