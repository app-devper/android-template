package com.devper.template.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.devper.template.domain.model.movie.Movie
import com.devper.template.core.platform.NetworkState
import com.devper.template.core.platform.PagedListResult
import com.devper.template.domain.usecase.movie.GetConfigUseCase
import com.devper.template.domain.usecase.movie.GetMoviesUseCase
import com.devper.template.presentation.BaseViewModel
import java.util.concurrent.Executors

class MovieViewModel internal constructor(
    private val getConfigUseCase: GetConfigUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel() {
    private val pagedListResult: MutableLiveData<PagedListResult<Movie>> = MutableLiveData()

    val movieList: LiveData<PagedList<Movie>> = Transformations.switchMap(pagedListResult) { it.result }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(pagedListResult) { it.networkState }
    val isInitialLoading: LiveData<Boolean> = Transformations.switchMap(pagedListResult) { it.isInitialLoading }

    fun getMovies() {
        val dataSourceFactory = MovieDataSourceFactory(getMoviesUseCase)
        val result = dataSourceFactory.toLiveData(pageSize = 20, fetchExecutor = Executors.newFixedThreadPool(3))
        pagedListResult.value = PagedListResult(result, dataSourceFactory.isInitialLoading, dataSourceFactory.networkState)
    }

    override fun onCleared() {
        super.onCleared()
        getConfigUseCase.unsubscribe()
        getMoviesUseCase.unsubscribe()
    }

}
