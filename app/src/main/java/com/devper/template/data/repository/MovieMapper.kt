package com.devper.template.data.repository

import com.devper.template.data.remote.movie.*
import com.devper.template.domain.model.movie.*

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

    fun toMovieDomain(data: MovieData, imagesData: ImagesData?): Movie {
        val genres = toDomain(data.genres)
        return Movie(
            data.id, data.adult, data.backdropPath, genres, data.genreIds,
            data.homepage, data.originalLanguage, data.originalTitle,
            data.overview, data.popularity, imagesData?.baseUrlFull + data.posterPath,
            data.releaseDate,
            data.revenue, data.runtime, data.status, data.tagLine, data.title,
            data.video, data.voteAverage, data.voteCount
        )
    }

    fun toMoviesDomain(data: MoviesData, imagesData: ImagesData?): Movies {
        val results = toMovieDomain(data.results, imagesData)
        return Movies(data.page, results, data.totalResults, data.totalPages)
    }

    private fun toMovieDomain(data: List<MovieData>?, imagesData: ImagesData?): List<Movie>? {
        return data?.map { toMovieDomain(it, imagesData) }
    }

    private fun toDomain(data: List<GenreData>?): List<Genre>? {
        return data?.map { Genre(it.id, it.name) }
    }

}