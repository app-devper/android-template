package com.devper.template.app.api

import com.devper.template.app.model.DataResponse
import com.devper.template.app.model.User
import com.devper.template.login.model.Login
import com.devper.template.login.model.LoginRequest
import com.devper.template.member.model.Member

import com.devper.template.member.model.Result
import com.devper.template.signup.model.SignupRequest
import retrofit2.Call
import retrofit2.http.*

interface AppService {

    @POST("api/auth")
    fun login(@Body request: LoginRequest): Call<DataResponse<Login>>

    @POST("api/users/register")
    fun signup(@Body request: SignupRequest): Call<DataResponse<User>>

    @GET("api/users/{id}")
    fun getUserId(@Path("id") id: String): Call<DataResponse<User>>

    @GET("api/members")
    fun members(): Call<DataResponse<Result<Member>>>

    @GET
    fun members(@Url url: String): Call<DataResponse<Result<Member>>>
}
