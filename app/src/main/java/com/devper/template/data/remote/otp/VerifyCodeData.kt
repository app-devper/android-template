package com.devper.template.data.remote.otp

import com.google.gson.annotations.SerializedName

data class VerifyCodeData(
    @SerializedName("actionToken")
    val actionToken: String
)