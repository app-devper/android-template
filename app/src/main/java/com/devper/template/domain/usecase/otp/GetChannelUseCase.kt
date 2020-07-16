package com.devper.template.domain.usecase.otp

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.otp.OtpChannel
import com.devper.template.domain.repository.OtpRepository
import com.devper.template.domain.usecase.UseCase

class GetChannelUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: OtpRepository
) : UseCase<String?, OtpChannel>(dispatcher) {
    override suspend fun executeOnBackground(param: String?): OtpChannel {
        return repo.getChannel(param)
    }
}