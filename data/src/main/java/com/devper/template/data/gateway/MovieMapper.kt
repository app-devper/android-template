package com.devper.template.data.gateway

import com.devper.template.core.model.movie.*
import com.devper.template.data.remote.movie.*

class MovieMapper {

    fun toConfigurationDomain(data: ConfigurationData): Configuration {
        val images = toImagesDomain(data.images)
        return Configuration(images, data.changeKeys)
    }

    private fun toImagesDomain(data: ImagesData): Images {
        return Images(
            data.baseUrl, data.secureBaseUrl, data.backdropSizes, data.logoSizes,
            data.posterSizes, data.profileSizes, data.stillSizes
        )
    }

    fun toMovieDomain(data: MovieData,  images: Images): Movie {
        val genres = toDomain(data.genres)
        return Movie(
            data.id, data.adult, data.backdropPath, genres, data.genreIds,
            data.homepage, data.originalLanguage, data.originalTitle,
            data.overview, data.popularity, images.baseUrlFull + data.posterPath,
            data.releaseDate,
            data.revenue, data.runtime, data.status, data.tagLine, data.title,
            data.video, data.voteAverage, data.voteCount
        )
    }

    fun toMoviesDomain(data: MoviesData, images: Images): Movies {
        val results = toMovieDomain(data.results, images)
        return Movies(data.page, results, data.totalResults, data.totalPages)
    }

    private fun toMovieDomain(data: List<MovieData>?, images: Images): List<Movie>? {
        return data?.map { toMovieDomain(it, images) }
    }

    private fun toDomain(data: List<GenreData>?): List<Genre>? {
        return data?.map { Genre(it.id, it.name) }
    }

}