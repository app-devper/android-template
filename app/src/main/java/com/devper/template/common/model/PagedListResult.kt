package com.devper.template.common.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.devper.template.common.util.NetworkState

class PagedListResult<T>(
    val result: LiveData<PagedList<T>> = MutableLiveData(),
    val isInitialLoading: LiveData<Boolean> = MutableLiveData(),
    val networkState: LiveData<NetworkState> = MutableLiveData()
)