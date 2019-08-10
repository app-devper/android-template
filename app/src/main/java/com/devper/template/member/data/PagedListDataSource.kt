package com.devper.template.member.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.devper.common.api.ApiResponse
import com.devper.common.toApiResponse
import com.devper.template.common.util.NetworkState
import com.devper.template.member.MemberService
import com.devper.template.member.model.Member
import com.devper.template.member.model.MemberResponse

class PagedListDataSource(
    private val headers: Map<String, String>, private val api: MemberService
) : PageKeyedDataSource<String, Member>() {

    val isInitialLoading = MutableLiveData<Boolean>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Member>) {
        networkState.postValue(NetworkState.LOADING)
        isInitialLoading.postValue(true)
        api.members(headers).toApiResponse({
            it.let { response ->
                isInitialLoading.postValue(false)
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(response.body!!.data!!.results!!, 0, response.body!!.data!!.count, response.body!!.data!!.previous, response.body!!.data!!.next)
            }
        }, this::setInitialError)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Member>) {
        networkState.postValue(NetworkState.LOADING)
        api.members(params.key, headers).toApiResponse({
            it.let { response ->
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(response.body!!.data!!.results!!, response.body!!.data!!.next)
            }
        }, this::setError)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Member>) {
    }

    private fun setInitialError(error: ApiResponse<MemberResponse>) {
        isInitialLoading.postValue(false)
        networkState.postValue(NetworkState.error(error.errorMessage))
    }

    private fun setError(error: ApiResponse<MemberResponse>) {
        networkState.postValue(NetworkState.error(error.errorMessage))
    }

}