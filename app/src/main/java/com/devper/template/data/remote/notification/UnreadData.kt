package com.devper.template.data.remote.notification

import com.google.gson.annotations.SerializedName

data class UnreadData(
    @SerializedName("unread")
    val unread: Int
)