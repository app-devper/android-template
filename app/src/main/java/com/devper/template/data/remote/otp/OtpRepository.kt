package com.devper.template.data.remote.otp

import com.devper.template.domain.model.otp.*

interface OtpRepository {

    suspend fun getChannel(param: String?): OtpChannel

    suspend fun verifyUser(param: VerifyUserParam): VerifyUser

    suspend fun verifyCode(param: VerifyCodeParam): VerifyCode

}