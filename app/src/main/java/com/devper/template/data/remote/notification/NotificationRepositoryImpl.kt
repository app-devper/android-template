package com.devper.template.data.remote.notification

import com.devper.template.data.remote.ApiService
import com.devper.template.domain.model.notification.Notification
import com.devper.template.domain.model.notification.Notifications
import com.devper.template.domain.model.notification.SubscriptionParam
import com.devper.template.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : NotificationRepository {

    override suspend fun subscription(param: SubscriptionParam) {
        val mapper = NotificationMapper()
        return api.subscription(mapper.toRequest(param))
    }

    override suspend fun getNotifications(page: Int): Notifications {
        val mapper = NotificationMapper()
        return api.getNotifications(page).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun getNotification(id: String): Notification {
        val mapper = NotificationMapper()
        return api.getNotification(id).let {
            mapper.toDomain(it)
        }
    }

    override suspend fun getUnread(): Int {
        return api.getUnread().unread
    }

}