package com.devper.template.domain.usecase.otp

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.otp.*
import com.devper.template.domain.repository.OtpRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: OtpRepository
) : FlowUseCase<VerifyCodeParam, VerifyCode>(dispatcher.io()) {

    override fun execute(params: VerifyCodeParam): Flow<ResultState<VerifyCode>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.verifyCode(params)))
        }
    }
}