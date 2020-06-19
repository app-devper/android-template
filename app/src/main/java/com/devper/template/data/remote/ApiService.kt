package com.devper.template.data.remote

import com.devper.template.data.remote.device.RegisterData
import com.devper.template.data.remote.device.RegisterRequest
import com.devper.template.data.remote.movie.ConfigurationData
import com.devper.template.data.remote.movie.MovieData
import com.devper.template.data.remote.movie.MoviesData
import com.devper.template.data.remote.user.LoginData
import com.devper.template.data.remote.user.LoginRequest
import com.devper.template.data.remote.user.SignupRequest
import com.devper.template.data.remote.user.UserData
import retrofit2.http.*

interface ApiService {

    @POST("api/auth")
    suspend fun login(@Body request: LoginRequest): LoginData

    @POST("api/user/register")
    suspend fun signup(@Body request: SignupRequest): UserData

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
}
