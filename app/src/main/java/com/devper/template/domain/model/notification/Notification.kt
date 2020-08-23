package com.devper.template.domain.model.notification

import com.devper.template.core.extension.toWrapDateTime

data class Notification(
    val id: String,
    val receiver: String,
    val title: String,
    val body: String,
    val status: String,
    val action: String,
    val createdDate: String,
) {
    val displayCreatedDate = createdDate.toWrapDateTime()
}