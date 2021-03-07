package com.devper.template.data.remote

import com.devper.template.data.remote.auth.*
import com.devper.template.data.remote.device.RegisterData
import com.devper.template.data.remote.device.RegisterRequest
import com.devper.template.data.remote.notification.NotificationData
import com.devper.template.data.remote.notification.NotificationsData
import com.devper.template.data.remote.notification.SubscriptionRequest
import com.devper.template.data.remote.notification.UnreadData
import com.devper.template.data.remote.otp.*
import com.devper.template.data.remote.termcondition.TermConditionData
import com.devper.template.data.remote.user.SignUpRequest
import com.devper.template.data.remote.user.UserData
import com.devper.template.data.remote.user.UserRequest
import com.devper.template.data.remote.user.UsersData
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

    @POST("api/auth/register")
    suspend fun signUp(@Body request: SignUpRequest)

    @GET("api/auth/action-info")
    suspend fun actionInfo(@Header("x-action-token") actionToken: String): UserData

    @GET("api/auth/keep-alive")
    suspend fun keepAlive(): LoginData


    @GET("api/user/{id}")
    suspend fun getUserId(@Path("id") id: String): UserData

    @GET("api/user")
    suspend fun getUsers(@Query("page") page: Int): UsersData

    @GET("api/user/info")
    suspend fun getProfile(): UserData

    @PUT("api/user/info")
    suspend fun updateProfile(@Body request: UserRequest): UserData

    @PUT("api/user/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body request: UserRequest): UserData


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


    @POST("api/notification/subscription")
    suspend fun subscription(@Body request: SubscriptionRequest)

    @GET("api/notification")
    suspend fun getNotifications(@Query("page") page: Int): NotificationsData

    @GET("api/notification/{id}")
    suspend fun getNotification(@Path("id") id: String): NotificationData

    @GET("api/notification/unread")
    suspend fun getUnread(): UnreadData


    @GET("api/term-condition")
    suspend fun getTermCondition(): TermConditionData
}
