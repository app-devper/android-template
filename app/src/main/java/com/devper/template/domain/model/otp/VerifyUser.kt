package com.devper.template.domain.model.otp

data class VerifyUser(
    val refCode: String,
    val channel: String,
    val expiredDate: String,
    val userRefId: String
)