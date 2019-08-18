package com.devper.template.movie

import retrofit2.Call
import retrofit2.http.*
import com.devper.template.movie.model.Configuration
import com.devper.template.movie.model.Movie
import com.devper.template.movie.model.Movies

interface MovieService {

    @GET("3/movie/now_playing")
    fun getMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): Call<Movies>

    @GET("3/movie/upcoming")
    fun getUpcoming(@Query("page") page: Int, @Query("api_key") apiKey: String): Call<Movies>

    @GET("3/movie/{id}")
    fun getMovie(@Path("id") id: Int, @Query("api_key") apiKey: String): Call<Movie>

    @Headers("Cache-Control: public, max-stale=2419200") // 4 weeks
    @GET("3/configuration")
    fun getConfiguration(@Query("api_key") apiKey: String): Call<Configuration>

}
