package com.devper.template.data.remote

import com.devper.template.data.remote.auth.*
import com.devper.template.data.remote.device.RegisterData
import com.devper.template.data.remote.device.RegisterRequest
import com.devper.template.data.remote.movie.ConfigurationData
import com.devper.template.data.remote.movie.MovieData
import com.devper.template.data.remote.movie.MoviesData
import com.devper.template.data.remote.otp.*
import com.devper.template.data.remote.user.SignUpRequest
import com.devper.template.data.remote.user.UserData
import retrofit2.http.*

interface ApiService {

    @POST("api/auth")
    suspend fun login(@Body request: LoginRequest): LoginData

    @POST("api/auth/pin")
    suspend fun loginPin(@Body request: LoginPinRequest): LoginData

    @POST("api/auth/set-password")
    suspend fun setPassword(@Header("x-action-token") actionToken: String, @Body request: PasswordRequest)

    @POST("api/auth/verify-password")
    suspend fun verifyPassword(@Body request: PasswordRequest): VerifyData

    @POST("api/auth/set-pin")
    suspend fun setPin(@Header("x-action-token") actionToken: String, @Body request: PinRequest)

    @POST("api/auth/verify-pin")
    suspend fun verifyPin(@Body request: PinRequest): VerifyData

    @POST("api/user/register")
    suspend fun signUp(@Body request: SignUpRequest): UserData

    @GET("api/user/{id}")
    suspend fun getUserId(@Path("id") id: String): UserData

    @GET("api/user/info")
    suspend fun getProfile(): UserData

    @POST("api/user/{id}")
    suspend fun updateUser(@Path("id") id: String)

    @GET("api/moviedb/movie/now_playing")
    suspend fun getMovies(@Query("page") page: Int): MoviesData

    @GET("api/moviedb/movie/upcoming")
    suspend fun getUpcoming(@Query("page") page: Int): MoviesData

    @GET("api/moviedb/movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): MovieData

    @GET("api/moviedb/configuration")
    suspend fun getConfiguration(): ConfigurationData

    @POST("api/device")
    suspend fun registerDevice(@Body request: RegisterRequest): RegisterData

    @GET("api/otp/channel")
    suspend fun getOtpChannel(): OtpChannelData

    @GET("api/otp/channel/{username}")
    suspend fun getOtpChannel(@Path("username") username: String): OtpChannelData

    @POST("api/otp/verify-user")
    suspend fun verifyUser(@Body request: VerifyUserRequest): VerifyUserData

    @POST("api/otp/verify-code")
    suspend fun verifyCode(@Body request: VerifyCodeRequest): VerifyCodeData

    @GET("api/otp/code/{refCode}")
    suspend fun getCode(@Path("refCode") refCode: String): String
}
