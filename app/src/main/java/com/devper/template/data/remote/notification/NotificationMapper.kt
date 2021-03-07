package com.devper.template.data.remote.notification

import com.devper.template.domain.model.notification.*

class NotificationMapper {

    fun toRequest(it: SubscriptionParam): SubscriptionRequest {
        return SubscriptionRequest(it.deviceToken, it.channel)
    }

    fun toDomain(it: NotificationsData): Notifications {
        return Notifications(it.page, toDomain(it.results), it.total, it.totalPages)
    }

    private fun toDomain(it: List<NotificationData>): List<Notification> {
        return it.map {
            toDomain(it)
        }
    }

    fun toDomain(it: NotificationData): Notification {
        return Notification(it.id, it.receiver, it.title, it.body, it.status, it.action, it.createdDate)
    }

}