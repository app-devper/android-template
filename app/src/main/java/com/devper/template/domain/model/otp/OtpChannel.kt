package com.devper.template.domain.model.otp

data class OtpChannel(
    val channels: List<Channel>,
    val userRefId: String
)

