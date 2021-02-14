package com.devper.template.domain.usecase.user

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: UserRepository
) : FlowUseCase<SignUpParam, Unit>(dispatcher.io()) {

    override fun execute(params: SignUpParam): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            params.email = params.username
            emit(ResultState.Success(repo.signUp(params)))
        }
    }
}