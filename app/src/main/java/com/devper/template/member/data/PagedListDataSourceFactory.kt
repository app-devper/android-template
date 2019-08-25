package com.devper.template.member.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.devper.template.app.api.AppService
import com.devper.template.app.util.NetworkState
import com.devper.template.member.model.Member

class PagedListDataSourceFactory(api: AppService) : DataSource.Factory<String, Member>() {

    private var pagedListDataSource = PagedListDataSource(api)

    val isInitialLoading: LiveData<Boolean> = pagedListDataSource.isInitialLoading

    val networkState: LiveData<NetworkState> = pagedListDataSource.networkState

    override fun create(): DataSource<String, Member> = pagedListDataSource

}