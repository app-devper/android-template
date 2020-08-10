package com.devper.template.domain.model.otp

import com.devper.template.core.extension.toWrapDateTime

data class VerifyUser(
    val refCode: String,
    val channel: String,
    val expiredDate: String,
    val userRefId: String,
    var code: String = ""
) {
    val displayExpiredDate = expiredDate.toWrapDateTime()
}
