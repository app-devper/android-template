package com.devper.template.data.remote

import com.devper.template.data.remote.core.DataResponse
import com.devper.template.data.remote.device.DeviceRequest
import com.devper.template.data.remote.device.DeviceData
import com.devper.template.data.remote.movie.ConfigurationData
import com.devper.template.data.remote.movie.MovieData
import com.devper.template.data.remote.movie.MoviesData
import com.devper.template.data.remote.user.LoginData
import com.devper.template.data.remote.user.LoginRequest
import com.devper.template.data.remote.user.SignupRequest
import com.devper.template.data.remote.user.UserData
import retrofit2.http.*

interface AppService {

    @POST("api/auth")
    suspend fun login(@Body request: LoginRequest): DataResponse<LoginData>

    @POST("api/users/register")
    suspend fun signup(@Body request: SignupRequest): DataResponse<UserData>

    @GET("api/users/{id}")
    suspend fun getUserId(@Path("id") id: String): DataResponse<UserData>

    @POST("api/users/{id}")
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
    suspend fun registerDevice(@Body request: DeviceRequest): DataResponse<DeviceData>
}
