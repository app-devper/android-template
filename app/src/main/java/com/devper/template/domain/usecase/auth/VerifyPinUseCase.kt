package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.auth.PinParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.UseCase

class VerifyPinUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: AuthRepository
) : UseCase<PinParam, Verify>(dispatcher) {
    override suspend fun executeOnBackground(param: PinParam): Verify {
        return repo.verifyPin(param)
    }
}