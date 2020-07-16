package com.devper.template.data.remote.otp

import com.google.gson.annotations.SerializedName

data class ChannelData(
    @SerializedName("channel")
    val channel: String,
    @SerializedName("value")
    val value: String
)