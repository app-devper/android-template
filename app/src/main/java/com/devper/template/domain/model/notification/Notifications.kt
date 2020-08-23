package com.devper.template.domain.model.notification

data class Notifications(
    val page: Int,
    val results: List<Notification>,
    val total: Int,
    val totalPages: Int
)