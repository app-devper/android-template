package com.devper.template.domain.usecase.otp

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.otp.*
import com.devper.template.domain.repository.OtpRepository
import com.devper.template.domain.usecase.UseCase

class VerifyCodeUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: OtpRepository
) : UseCase<VerifyCodeParam, VerifyCode>(dispatcher) {
    override suspend fun executeOnBackground(param: VerifyCodeParam): VerifyCode {
        return repo.verifyCode(param)
    }
}