package com.devper.template.data.remote.user

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("accessToken")
    val accessToken: String
)

