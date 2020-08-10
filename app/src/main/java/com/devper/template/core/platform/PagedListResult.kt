package com.devper.template.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

class PagedListResult<T>(
    val result: LiveData<PagedList<Any>> = MutableLiveData(),
    val isInitialLoading: LiveData<Boolean> = MutableLiveData(),
    val networkState: LiveData<NetworkState> = MutableLiveData()
)