package com.devper.template.domain.usecase.notification

import com.devper.template.data.remote.notification.NotificationRepository
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.notification.SubscriptionParam
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class SubscriptionUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: NotificationRepository
) : UseCase<SubscriptionParam, Unit>(dispatcher.io()) {

    override suspend fun execute(params: SubscriptionParam) {
        repo.subscription(params)
    }
}