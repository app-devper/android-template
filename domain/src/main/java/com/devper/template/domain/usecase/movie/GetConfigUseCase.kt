package com.devper.template.domain.usecase.movie

import com.devper.template.core.model.movie.Configuration
import com.devper.template.core.repository.MovieRepository
import com.devper.template.domain.usecase.UseCase

class GetConfigUseCase(private val repo: MovieRepository) : UseCase<Int?, Configuration>() {
    override suspend fun executeOnBackground(param: Int?): Configuration {
        return repo.getConfiguration()
    }
}