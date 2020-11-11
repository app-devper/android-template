package com.devper.template.data.remote.user

import com.devper.template.data.database.AppDatabase
import com.devper.template.data.remote.ApiService
import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.UserUpdateParam
import com.devper.template.domain.model.user.Users
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: AppDatabase,
) : UserRepository {

    override suspend fun clearUser() {
        return db.user().clearUser()
    }

    override suspend fun getCurrentUser(): User? {
        val mapper = UserMapper()
        return db.user().getFirstUser()?.let {
            mapper.toDomain(it)
        }
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
        return api.signUp(mapper.toRequest(request))
    }

    override suspend fun getProfile(): User {
        val mapper = UserMapper()
        return api.getProfile().let {
            db.user().addUser(it)
            mapper.toDomain(it)
        }
    }

    override suspend fun updateProfile(param: UserUpdateParam): User {
        val mapper = UserMapper()
        return api.updateProfile(mapper.toRequest(param)).let {
            db.user().addUser(it)
            mapper.toDomain(it)
        }
    }

    override suspend fun updateUser(param: UserUpdateParam): User {
        val mapper = UserMapper()
        return api.updateUser(param.id, mapper.toRequest(param)).let {
            mapper.toDomain(it)
        }
    }

}