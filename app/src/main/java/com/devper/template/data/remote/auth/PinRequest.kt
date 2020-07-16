package com.devper.template.data.remote.auth

import com.google.gson.annotations.SerializedName

data class PinRequest(
    @SerializedName("pin")
    val pin: String
)