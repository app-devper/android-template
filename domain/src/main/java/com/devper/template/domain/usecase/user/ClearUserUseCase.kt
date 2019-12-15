package com.devper.template.domain.usecase.user

import com.devper.template.core.repository.UserRepository
import com.devper.template.domain.usecase.UseCase

class ClearUserUseCase(private val repo: UserRepository) : UseCase<String?, Unit>() {
    override suspend fun executeOnBackground(param: String?) {
        return repo.clearUser()
    }
}