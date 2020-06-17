package com.devper.template.presentation.movie.datasource

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.devper.template.core.platform.NetworkState
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.usecase.movie.GetMoviesUseCase
import com.devper.template.presentation.movie.datasource.MovieDataSource

class MovieDataSourceFactory(useCase: GetMoviesUseCase) : DataSource.Factory<Int, Movie>() {

    private var pagedListDataSource = MovieDataSource(useCase)

    val isInitialLoading: LiveData<Boolean> = pagedListDataSource.isInitialLoading

    val networkState: LiveData<NetworkState> = pagedListDataSource.networkState

    override fun create(): DataSource<Int, Movie> = pagedListDataSource

}