package com.devper.template.data.remote.auth

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @SerializedName("password")
    val password: String
)