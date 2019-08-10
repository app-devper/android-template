package com.devper.template.movie.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.devper.common.api.ApiResponse
import com.devper.common.toApiResponse
import com.devper.template.common.util.NetworkState
import com.devper.template.movie.MovieService
import com.devper.template.movie.model.Movie
import com.devper.template.movie.model.Movies

class MovieDataSource(
    private val apiKey: String, private val api: MovieService
) : PageKeyedDataSource<Int, Movie>() {

    val isInitialLoading = MutableLiveData<Boolean>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        isInitialLoading.postValue(true)
        api.getMovies(1, apiKey).toApiResponse({
            it.let { response ->
                isInitialLoading.postValue(false)
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(response.body!!.results!!, 0, response.body!!.totalResults, 1, response.body!!.page + 1)
            }
        }, this::setInitialError)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        api.getMovies(params.key, apiKey).toApiResponse({
            it.let { response ->
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(response.body!!.results!!, response.body!!.page + 1)
            }
        }, this::setError)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    private fun setInitialError(error: ApiResponse<Movies>) {
        isInitialLoading.postValue(false)
        networkState.postValue(NetworkState.error(error.errorMessage))
    }

    private fun setError(error: ApiResponse<Movies>) {
        networkState.postValue(NetworkState.error(error.errorMessage))
    }

}