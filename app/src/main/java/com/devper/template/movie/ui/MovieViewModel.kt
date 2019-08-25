package com.devper.template.movie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.devper.common.api.AbsentLiveData
import com.devper.common.api.Resource
import com.devper.template.app.model.PagedListResult
import com.devper.template.app.util.NetworkState
import com.devper.template.movie.data.MovieRepository
import com.devper.template.movie.model.Configuration
import com.devper.template.movie.model.Movie

class MovieViewModel internal constructor(private val repo: MovieRepository) : ViewModel() {

    private val config: MutableLiveData<Boolean> = MutableLiveData()
    private val pagedListResult: MutableLiveData<PagedListResult<Movie>> = MutableLiveData()
    val configResult: LiveData<Resource<Configuration>> = Transformations.switchMap(config) {
        when (it) {
            true -> repo.getConfiguration()
            else -> AbsentLiveData.create()
        }
    }

    val movieList: LiveData<PagedList<Movie>> = Transformations.switchMap(pagedListResult) { it.result }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(pagedListResult) { it.networkState }
    val isInitialLoading: LiveData<Boolean> = Transformations.switchMap(pagedListResult) { it.isInitialLoading }

    fun getMovies() {
        pagedListResult.value = repo.getMovies()
    }

    fun getConfiguration() {
        config.value = true
    }

}
