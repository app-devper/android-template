package com.devper.template.domain.usecase.otp

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.otp.OtpChannel
import com.devper.template.domain.repository.OtpRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChannelUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: OtpRepository
) : FlowUseCase<String?, OtpChannel>(dispatcher.io()) {

    override fun execute(parameters: String?): Flow<ResultState<OtpChannel>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.getChannel(parameters)))
        }
    }
}