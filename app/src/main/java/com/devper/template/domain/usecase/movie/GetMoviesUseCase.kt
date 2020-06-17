package com.devper.template.domain.usecase.movie

import com.devper.template.domain.model.movie.Movies
import com.devper.template.domain.usecase.UseCase

class GetMoviesUseCase(private val repo: com.devper.template.domain.repository.MovieRepository) :
    UseCase<Int, Movies>() {
    override suspend fun executeOnBackground(param: Int): com.devper.template.domain.model.movie.Movies {
        return repo.getMovies(param)
    }
}