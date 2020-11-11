package com.devper.template.domain.usecase.otp

import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.otp.VerifyUser
import com.devper.template.domain.model.otp.VerifyUserParam
import com.devper.template.data.remote.otp.OtpRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyUserUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: OtpRepository
) : FlowUseCase<VerifyUserParam, VerifyUser>(dispatcher.io()) {

    override fun execute(params: VerifyUserParam): Flow<ResultState<VerifyUser>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.verifyUser(params)))
        }
    }
}