package com.devper.template.movie;

import retrofit2.Call;
import retrofit2.http.*;
import com.devper.template.movie.model.Configuration;
import com.devper.template.movie.model.Movie;
import com.devper.template.movie.model.Movies;

public interface MovieService {

    @GET("3/movie/now_playing")
    Call<Movies> getMovies(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("3/movie/upcoming")
    Call<Movies> getUpcoming(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("3/movie/{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String apiKey);

    @Headers("Cache-Control: public, max-stale=2419200") // 4 weeks
    @GET("3/configuration")
    Call<Configuration> getConfiguration(@Query("api_key") String apiKey);

}
