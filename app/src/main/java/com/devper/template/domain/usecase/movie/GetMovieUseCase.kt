package com.devper.template.domain.usecase.movie

import com.devper.template.domain.repository.MovieRepository
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.usecase.UseCase

class GetMovieUseCase( private val repo: MovieRepository) :
    UseCase<Int, Movie>() {
    override suspend fun executeOnBackground(param: Int): Movie {
        return repo.getMovie(param)
    }
}