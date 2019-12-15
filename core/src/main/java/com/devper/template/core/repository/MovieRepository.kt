package com.devper.template.core.repository

import com.devper.template.core.model.movie.Configuration
import com.devper.template.core.model.movie.Movie
import com.devper.template.core.model.movie.Movies

interface MovieRepository {

    suspend fun getMovies(page: Int): Movies

    suspend fun getConfiguration(): Configuration

    suspend fun getMovie(id: Int): Movie

}