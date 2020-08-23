package com.devper.template.data.remote.notification

import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("_id")
    val id: String,
    @SerializedName("receiver")
    val receiver: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("action")
    val action: String,
    @SerializedName("createdDate")
    val createdDate: String,
)