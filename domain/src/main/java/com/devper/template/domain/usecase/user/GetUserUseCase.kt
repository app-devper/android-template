package com.devper.template.domain.usecase.user

import com.devper.template.core.repository.UserRepository
import com.devper.template.core.model.user.User
import com.devper.template.domain.usecase.UseCase

class GetUserUseCase(private val repo: UserRepository) : UseCase<String, User>() {
    override suspend fun executeOnBackground(param: String): User {
        return repo.getProfile(param)
    }
}