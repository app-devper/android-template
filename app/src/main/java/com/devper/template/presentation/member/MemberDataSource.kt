package com.devper.template.presentation.member

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.devper.template.core.util.NetworkState
import com.devper.template.data.remote.AppService
import com.devper.template.domain.model.member.Member
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberDataSource(private val api: AppService) : PageKeyedDataSource<String, Member>() {

    val isInitialLoading = MutableLiveData<Boolean>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Member>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                networkState.postValue(NetworkState.LOADING)
                isInitialLoading.postValue(true)
                val result = api.members()
                isInitialLoading.postValue(false)
                networkState.postValue(NetworkState.LOADED)
                result.data?.let {
                    callback.onResult(it.results, 0, it.count, it.previous, it.next)
                }
            } catch (e: Exception) {
                isInitialLoading.postValue(false)
                networkState.postValue(NetworkState.error(e.message))
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Member>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                networkState.postValue(NetworkState.LOADING)
                val result = api.members(params.key)
                networkState.postValue(NetworkState.LOADED)
                result.data?.let {
                    callback.onResult(it.results, it.next)
                }
            } catch (e: Exception) {
                networkState.postValue(NetworkState.error(e.message))
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Member>) {
    }

}