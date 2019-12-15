package com.devper.template.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.devper.template.core.model.movie.Movie
import com.devper.template.core.platform.NetworkState
import com.devper.template.core.platform.PagedListResult
import com.devper.template.domain.usecase.movie.GetConfigUseCase
import com.devper.template.domain.usecase.movie.GetMoviesUseCase
import java.util.concurrent.Executors

class MovieViewModel internal constructor(
    private val useCase: GetConfigUseCase,
    private val moviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private val pagedListResult: MutableLiveData<PagedListResult<Movie>> = MutableLiveData()

    val movieList: LiveData<PagedList<Movie>> = Transformations.switchMap(pagedListResult) { it.result }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(pagedListResult) { it.networkState }
    val isInitialLoading: LiveData<Boolean> = Transformations.switchMap(pagedListResult) { it.isInitialLoading }

    init {
        getMovies()
    }

    private fun getMovies() {
        val dataSourceFactory = MovieDataSourceFactory(moviesUseCase)
        val result = dataSourceFactory.toLiveData(pageSize = 20, fetchExecutor = Executors.newFixedThreadPool(3))
        pagedListResult.value = PagedListResult(result, dataSourceFactory.isInitialLoading, dataSourceFactory.networkState)
    }

    override fun onCleared() {
        super.onCleared()
        useCase.unsubscribe()
        moviesUseCase.unsubscribe()
    }

}
