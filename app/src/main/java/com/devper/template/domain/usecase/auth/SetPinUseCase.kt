package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.auth.SetPinParam
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.UseCase

class SetPinUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: AuthRepository
) : UseCase<SetPinParam, Unit>(dispatcher) {
    override suspend fun executeOnBackground(param: SetPinParam) {
        return repo.setPin(param)
    }
}