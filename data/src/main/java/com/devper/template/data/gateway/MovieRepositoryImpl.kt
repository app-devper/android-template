package com.devper.template.data.gateway

import com.devper.template.core.model.movie.Configuration
import com.devper.template.core.model.movie.Movie
import com.devper.template.core.model.movie.Movies
import com.devper.template.core.repository.MovieRepository
import com.devper.template.data.remote.ApiService
import com.devper.template.data.session.AppSession

class MovieRepositoryImpl(private val api: ApiService, private val session: AppSession) : MovieRepository {

    override suspend fun getMovies(page: Int): Movies {
        val mapper = MovieMapper()
        val config = getConfiguration()
        return safeApiCall { api.getMovies(page) }.let {
            mapper.toMoviesDomain(it, config.images)
        }
    }

    override suspend fun getConfiguration(): Configuration {
        val mapper = MovieMapper()
        val result = session.configurationData ?: safeApiCall { api.getConfiguration() }
        return result.let {
            session.configurationData = it
            mapper.toConfigurationDomain(it)
        }
    }

    override suspend fun getMovie(id: Int): Movie {
        val mapper = MovieMapper()
        val config = getConfiguration()
        return safeApiCall { api.getMovie(id) }.let {
            mapper.toMovieDomain(it, config.images)
        }
    }

}