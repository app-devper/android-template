package com.devper.template.domain.usecase.notification

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.notification.Notification
import com.devper.template.domain.model.notification.NotificationParam
import com.devper.template.domain.repository.NotificationRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: NotificationRepository
) : FlowUseCase<NotificationParam, Notification>(dispatcher.io()) {

    override fun execute(params: NotificationParam): Flow<ResultState<Notification>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.getNotification(params.id)))
        }
    }
}