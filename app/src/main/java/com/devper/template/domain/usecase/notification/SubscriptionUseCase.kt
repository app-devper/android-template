package com.devper.template.domain.usecase.notification

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.notification.SubscriptionParam
import com.devper.template.domain.repository.NotificationRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubscriptionUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: NotificationRepository
) : FlowUseCase<SubscriptionParam, Unit>(dispatcher.io()) {

    override fun execute(parameters: SubscriptionParam): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.subscription(parameters)))
        }
    }
}