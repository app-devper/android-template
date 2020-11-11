package com.devper.template.domain.usecase.auth

import com.devper.template.core.extension.md5
import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.auth.PasswordParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyPasswordUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository
) : FlowUseCase<PasswordParam, Verify>(dispatcher.io()) {

    override fun execute(params: PasswordParam): Flow<ResultState<Verify>> {
        return flow {
            emit(ResultState.Loading())
            params.password =  params.password.md5()
            emit(ResultState.Success(repo.verifyPassword(params)))
        }
    }
}