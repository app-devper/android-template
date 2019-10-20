package com.devper.template.domain.repository

import com.devper.template.domain.model.movie.Configuration
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.model.movie.Movies

interface MovieRepository {

    suspend fun getMovies(page: Int): Movies

    suspend fun getConfiguration(): Configuration

    suspend fun getMovie(id: Int): Movie

}