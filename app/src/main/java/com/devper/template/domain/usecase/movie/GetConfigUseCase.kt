package com.devper.template.domain.usecase.movie

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.movie.Configuration
import com.devper.template.domain.repository.MovieRepository
import com.devper.template.domain.usecase.UseCase

class GetConfigUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: MovieRepository
) : UseCase<Int?, Configuration>(dispatcher) {
    override suspend fun executeOnBackground(param: Int?): Configuration {
        return repo.getConfiguration()
    }
}