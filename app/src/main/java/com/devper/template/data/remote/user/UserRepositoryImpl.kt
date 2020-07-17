package com.devper.template.data.remote.user

import com.devper.template.core.exception.AppException
import com.devper.template.data.database.AppDatabase
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.ApiService
import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.Users
import com.devper.template.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: ApiService,
    private val db: AppDatabase,
    private val pref: AppPreference
) : UserRepository {

    override suspend fun clearUser() {
        pref.clear()
        db.user().clearUser()
    }

    override suspend fun getCurrentUser(): User {
        val mapper = UserMapper()
        return db.user().getFirstUser()?.let {
            mapper.toDomain(it)
        } ?: throw AppException("USER_NOT_FOUND", "USER_NOT_FOUND")
    }

    override suspend fun getUsers(page: Int): Users {
        val mapper = UserMapper()
        return api.getUsers(page).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun getUser(userId: String): User {
        val mapper = UserMapper()
        return api.getUserId(userId).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun signUp(request: SignUpParam) {
        val mapper = UserMapper()
        api.signUp(mapper.toRequest(request)).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun getProfile(): User {
        val mapper = UserMapper()
        return api.getProfile().let {
            db.user().addUser(it)
            mapper.toDomain(it)
        }
    }

}