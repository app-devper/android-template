package com.devper.template.data.remote.notification

import com.google.gson.annotations.SerializedName

data class SubscriptionRequest(
    @SerializedName("deviceToken")
    val deviceToken: String,
    @SerializedName("channel")
    val channel: String,
)