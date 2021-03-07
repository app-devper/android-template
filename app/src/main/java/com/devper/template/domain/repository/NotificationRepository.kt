package com.devper.template.domain.repository

import com.devper.template.domain.model.notification.Notification
import com.devper.template.domain.model.notification.Notifications
import com.devper.template.domain.model.notification.SubscriptionParam

interface NotificationRepository {

    suspend fun subscription(param: SubscriptionParam)

    suspend fun getNotifications(page: Int): Notifications

    suspend fun getNotification(id: String): Notification

    suspend fun getUnread(): Int

}