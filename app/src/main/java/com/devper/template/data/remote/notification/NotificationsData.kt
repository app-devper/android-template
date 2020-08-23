package com.devper.template.data.remote.notification

import com.google.gson.annotations.SerializedName

data class NotificationsData(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<NotificationData>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)