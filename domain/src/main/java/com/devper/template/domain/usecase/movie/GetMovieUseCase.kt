package com.devper.template.domain.usecase.movie

import com.devper.template.core.repository.MovieRepository
import com.devper.template.core.model.movie.Movie
import com.devper.template.domain.usecase.UseCase

class GetMovieUseCase( private val repo: MovieRepository) :
    UseCase<Int, Movie>() {
    override suspend fun executeOnBackground(param: Int): Movie {
        return repo.getMovie(param)
    }
}