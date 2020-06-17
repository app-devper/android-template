package com.devper.template.domain.usecase.movie

import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.usecase.UseCase

class GetMovieUseCase( private val repo: com.devper.template.domain.repository.MovieRepository) :
    UseCase<Int, Movie>() {
    override suspend fun executeOnBackground(param: Int): com.devper.template.domain.model.movie.Movie {
        return repo.getMovie(param)
    }
}