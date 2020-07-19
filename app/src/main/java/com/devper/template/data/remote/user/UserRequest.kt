package com.devper.template.data.remote.user

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: String?
)