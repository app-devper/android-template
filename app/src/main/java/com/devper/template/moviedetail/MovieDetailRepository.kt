package com.devper.template.moviedetail

import androidx.lifecycle.LiveData
import com.devper.common.api.Resource
import com.devper.common.toResource
import com.devper.template.common.AppDatabase
import com.devper.template.movie.MovieService
import com.devper.template.movie.model.Movie

class MovieDetailRepository(val db: AppDatabase, private val service: MovieService) {
    private val apiKey: String = "3fa9058382669f72dcb18fb405b7a831"

    fun getMovie(id: Int): LiveData<Resource<Movie>> {
        return service.getMovie(id, apiKey).toResource()
    }
}