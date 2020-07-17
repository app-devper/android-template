package com.devper.template.presentation.user

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.devper.template.core.platform.NetworkState
import com.devper.template.domain.core.ErrorMapper
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.user.GetUsersUseCase

class UserDataSource(private val useCase: GetUsersUseCase) : PageKeyedDataSource<Int, User>() {

    val isInitialLoading = MutableLiveData<Boolean>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        useCase.execute(1) {
            onStart {
                isInitialLoading.postValue(true)
            }
            onComplete {
                isInitialLoading.postValue(false)
                callback.onResult(it.results, 0, it.total, 1, it.page + 1)
            }
            onError {
                isInitialLoading.postValue(false)
                val appError = ErrorMapper.toAppError(it)
                networkState.postValue(NetworkState.error(appError.getDesc()))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        useCase.execute(params.key) {
            onStart {
                networkState.postValue(NetworkState.LOADING)
            }
            onComplete {
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it.results, it.page + 1)
            }
            onError {
                val appError = ErrorMapper.toAppError(it)
                networkState.postValue(NetworkState.error(appError.getDesc()))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {}

}