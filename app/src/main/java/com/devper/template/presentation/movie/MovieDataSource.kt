package com.devper.template.presentation.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.devper.template.core.util.NetworkState
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.usecase.movie.GetMoviesUseCase

class MovieDataSource(private val useCase: GetMoviesUseCase) : PageKeyedDataSource<Int, Movie>() {

    val isInitialLoading = MutableLiveData<Boolean>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        useCase.execute(1){
            onStart {
                networkState.postValue(NetworkState.LOADING)
                isInitialLoading.postValue(true)
            }
            onComplete {
                isInitialLoading.postValue(false)
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it.results?: emptyList(), 0, it.totalResults, 1, it.page + 1)
            }
            onError {
                isInitialLoading.postValue(false)
                networkState.postValue(NetworkState.error(it.message))
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        useCase.execute(params.key){
            onStart {
                isInitialLoading.postValue(true)
            }
            onComplete {
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it.results?: emptyList(), it.page + 1)
            }
            onError {
                networkState.postValue(NetworkState.error(it.message))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

}