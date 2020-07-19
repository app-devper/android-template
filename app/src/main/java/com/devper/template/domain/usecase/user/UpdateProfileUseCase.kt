package com.devper.template.domain.usecase.user

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.UserUpdateParam
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.UseCase

class UpdateProfileUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: UserRepository
) : UseCase<UserUpdateParam, User>(dispatcher) {
    override suspend fun executeOnBackground(param: UserUpdateParam): User {
        return repo.updateProfile(param)
    }
}