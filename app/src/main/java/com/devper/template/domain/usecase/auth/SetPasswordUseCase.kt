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
    override fun execute(parameters: SetPasswordParam): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            if (parameters.actionToken.isEmpty() || parameters.password.isEmpty()) {
                throw AppException(LOGIN_ERROR, "")
            }
            parameters.password = parameters.password.md5()
            emit(ResultState.Success(repo.setPassword(parameters)))
        }
    }
}