package com.devper.template.domain.usecase.user

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.user.User
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.UseCase

class GetProfileUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: UserRepository
) : UseCase<String?, User>(dispatcher) {
    override suspend fun executeOnBackground(param: String?): User {
        return repo.getProfile()
    }
}