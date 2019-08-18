package com.devper.template.signup.model

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("phone")
    var phone: String? = null
)