package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.user.User
import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetActionInfoUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository
) : FlowUseCase<String, User>(dispatcher.io()) {

    override fun execute(params: String): Flow<ResultState<User>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.actionInfo(params)))
        }
    }
}