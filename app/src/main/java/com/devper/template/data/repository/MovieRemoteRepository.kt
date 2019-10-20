package com.devper.template.data.repository

import com.devper.template.data.remote.AppService
import com.devper.template.data.session.AppSession
import com.devper.template.domain.model.movie.Configuration
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.model.movie.Movies
import com.devper.template.domain.repository.MovieRepository

class MovieRemoteRepository(private val api: AppService, private val session: AppSession) : MovieRepository {

    override suspend fun getMovies(page: Int): Movies {
        val mapper = MovieMapper()
        if (session.imagesData == null) {
            getConfiguration().also {
                return api.getMovies(page).let {
                    mapper.toMoviesDomain(it, session.imagesData)
                }
            }
        } else {
            return api.getMovies(page).let {
                mapper.toMoviesDomain(it, session.imagesData)
            }
        }
    }

    override suspend fun getConfiguration(): Configuration {
        val mapper = MovieMapper()
        return api.getConfiguration().let {
            session.imagesData = it.images
            mapper.toConfigurationDomain(it)
        }
    }

    override suspend fun getMovie(id: Int): Movie {
        val mapper = MovieMapper()
        if (session.imagesData == null) {
            getConfiguration().also {
                return api.getMovie(id).let {
                    mapper.toMovieDomain(it, session.imagesData)
                }
            }
        } else {
            return api.getMovie(id).let {
                mapper.toMovieDomain(it, session.imagesData)
            }
        }
    }

}