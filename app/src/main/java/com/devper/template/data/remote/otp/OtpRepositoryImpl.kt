package com.devper.template.data.remote.otp

import com.devper.template.data.remote.ApiService
import com.devper.template.domain.model.otp.*
import com.devper.template.domain.repository.OtpRepository

class OtpRepositoryImpl(
    private val api: ApiService
) : OtpRepository {

    override suspend fun getChannel(param: String?): OtpChannel {
        val mapper = OtpMapper()
        val data = if (param.isNullOrEmpty()) {
            api.getOtpChannel()
        } else {
            api.getOtpChannel(param)
        }
        return data.let {
            mapper.toDomain(it)
        }
    }

    override suspend fun verifyUser(param: VerifyUserParam): VerifyUser {
        val mapper = OtpMapper()
        return api.verifyUser(mapper.toRequest(param)).let {
            api.getCode(it.refCode)
            mapper.toDomain(it)
        }
    }

    override suspend fun verifyCode(param: VerifyCodeParam): VerifyCode {
        val mapper = OtpMapper()
        return api.verifyCode(mapper.toRequest(param)).let {
            mapper.toDomain(it)
        }
    }
}