package com.devper.template.data.remote.auth

import com.google.gson.annotations.SerializedName

data class VerifyData(
    @SerializedName("actionToken")
    val actionToken: String
)