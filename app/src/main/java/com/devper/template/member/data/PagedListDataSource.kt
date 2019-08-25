package com.devper.template.member.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.devper.common.api.ApiResponse
import com.devper.common.toApiResponse
import com.devper.template.app.api.AppService
import com.devper.template.app.model.DataResponse
import com.devper.template.app.util.NetworkState
import com.devper.template.app.util.data
import com.devper.template.member.model.Member
import com.devper.template.member.model.Result

class PagedListDataSource(private val api: AppService
) : PageKeyedDataSource<String, Member>() {

    val isInitialLoading = MutableLiveData<Boolean>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Member>) {
        networkState.postValue(NetworkState.LOADING)
        isInitialLoading.postValue(true)
        api.members().toApiResponse({
            it.data()?.let { result ->
                isInitialLoading.postValue(false)
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(result.results, 0, result.count, result.previous, result.next)
            }
        }, this::setInitialError)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Member>) {
        networkState.postValue(NetworkState.LOADING)
        api.members(params.key).toApiResponse({
            it.data()?.let { result ->
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(result.results, result.next)
            }
        }, this::setError)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Member>) {
    }

    private fun setInitialError(error: ApiResponse<DataResponse<Result<Member>>>) {
        isInitialLoading.postValue(false)
        networkState.postValue(NetworkState.error(error.errorMessage))
    }

    private fun setError(error: ApiResponse<DataResponse<Result<Member>>>) {
        networkState.postValue(NetworkState.error(error.errorMessage))
    }

}