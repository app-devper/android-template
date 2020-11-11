package com.devper.template.domain.usecase.user

import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.UserUpdateParam
import com.devper.template.data.remote.user.UserRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: UserRepository
) : FlowUseCase<UserUpdateParam, User>(dispatcher.io()) {

    override fun execute(params: UserUpdateParam): Flow<ResultState<User>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.updateUser(params)))
        }
    }
}