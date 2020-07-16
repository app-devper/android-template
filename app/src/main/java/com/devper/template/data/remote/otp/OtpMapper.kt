package com.devper.template.data.remote.otp

import com.devper.template.domain.model.otp.*

class OtpMapper {

    fun toDomain(data: OtpChannelData): OtpChannel {
        return OtpChannel(toDomain(data.channels), data.userRefId)
    }

    private fun toDomain(data: List<ChannelData>): List<Channel> {
        return data.map {
            Channel(it.channel, it.value)
        }
    }

    fun toRequest(data: VerifyUserParam): VerifyUserRequest {
        return VerifyUserRequest(data.userRefId, data.channel)
    }

    fun toDomain(data: VerifyUserData): VerifyUser {
        return VerifyUser(data.refCode, data.channel, data.expiredDate, data.userRefId)
    }

    fun toRequest(data: VerifyCodeParam): VerifyCodeRequest {
        return VerifyCodeRequest(data.userRefId ?: "", data.refCode ?: "", data.code ?: "")
    }

    fun toDomain(data: VerifyCodeData): VerifyCode {
        return VerifyCode(data.actionToken)
    }

}