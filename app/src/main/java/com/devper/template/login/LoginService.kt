package com.devper.template.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

import com.devper.template.login.model.LoginRequest
import com.devper.template.login.model.LoginResponse

interface LoginService {

    @POST("api/auth")
    fun login(@HeaderMap headers: Map<String, String>, @Body request: LoginRequest): Call<LoginResponse>

}
