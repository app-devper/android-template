package com.devper.template.member

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url
import com.devper.template.member.model.MemberResponse

interface MemberService {

    @GET("api/members")
    fun members(@HeaderMap headers: Map<String, String>): Call<MemberResponse>

    @GET
    fun members(@Url url: String, @HeaderMap headers: Map<String, String>): Call<MemberResponse>
}
