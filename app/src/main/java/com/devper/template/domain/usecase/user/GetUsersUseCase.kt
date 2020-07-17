package com.devper.template.domain.usecase.user

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.user.Users
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.UseCase

class GetUsersUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: UserRepository
) : UseCase<Int, Users>(dispatcher) {
    override suspend fun executeOnBackground(param: Int): Users {
        return repo.getUsers(param)
    }
}