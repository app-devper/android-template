package com.devper.template.domain.usecase.otp

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.otp.OtpChannel
import com.devper.template.domain.model.otp.VerifyUser
import com.devper.template.domain.model.otp.VerifyUserParam
import com.devper.template.domain.repository.OtpRepository
import com.devper.template.domain.usecase.UseCase

class VerifyUserUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: OtpRepository
) : UseCase<VerifyUserParam, VerifyUser>(dispatcher) {
    override suspend fun executeOnBackground(param: VerifyUserParam): VerifyUser {
        return repo.verifyUser(param)
    }
}