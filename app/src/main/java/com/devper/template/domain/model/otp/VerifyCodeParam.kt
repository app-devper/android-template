package com.devper.template.domain.model.otp

data class VerifyCodeParam(
    var userRefId: String? = "",
    var refCode: String? = "",
    var code: String? = ""
)