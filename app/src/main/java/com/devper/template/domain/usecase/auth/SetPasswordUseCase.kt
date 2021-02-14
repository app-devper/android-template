package com.devper.template.domain.usecase.auth

import com.devper.template.AppConfig.LOGIN_ERROR
import com.devper.template.core.exception.AppException
import com.devper.template.core.extension.md5
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.auth.SetPasswordParam
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetPasswordUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository
) : FlowUseCase<SetPasswordParam, Unit>(dispatcher.io()) {
    override fun execute(params: SetPasswordParam): Flow<ResultState<Unit>> {
        return flow {
            if (params.actionToken.isEmpty() || params.password.isEmpty()) {
                throw AppException(LOGIN_ERROR, "")
            }
            emit(ResultState.Loading())
            params.password = params.password.md5()
            emit(ResultState.Success(repo.setPassword(params)))
        }
    }
}