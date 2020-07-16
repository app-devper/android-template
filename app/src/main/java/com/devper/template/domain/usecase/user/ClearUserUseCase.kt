package com.devper.template.domain.usecase.user

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.UseCase

class ClearUserUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: UserRepository
) : UseCase<String?, Unit>(dispatcher) {
    override suspend fun executeOnBackground(param: String?) {
        return repo.clearUser()
    }
}