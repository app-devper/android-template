package com.devper.template.domain.usecase.notification

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.repository.NotificationRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUnreadUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: NotificationRepository
) : FlowUseCase<Unit, Int>(dispatcher.io()) {

    override fun execute(parameters: Unit): Flow<ResultState<Int>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.getUnread()))
        }
    }
}