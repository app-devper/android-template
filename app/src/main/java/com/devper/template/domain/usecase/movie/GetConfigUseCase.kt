package com.devper.template.domain.usecase.movie

import com.devper.template.domain.model.movie.Configuration
import com.devper.template.domain.usecase.UseCase

class GetConfigUseCase(private val repo: com.devper.template.domain.repository.MovieRepository) : UseCase<Int?, Configuration>() {
    override suspend fun executeOnBackground(param: Int?): com.devper.template.domain.model.movie.Configuration {
        return repo.getConfiguration()
    }
}