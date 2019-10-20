package com.devper.template.presentation.member

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.devper.template.data.remote.AppService
import com.devper.template.core.util.NetworkState
import com.devper.template.domain.model.member.Member

class MemberDataSourceFactory(api: AppService) : DataSource.Factory<String, Member>() {

    private var pagedListDataSource = MemberDataSource(api)

    val isInitialLoading: LiveData<Boolean> = pagedListDataSource.isInitialLoading

    val networkState: LiveData<NetworkState> = pagedListDataSource.networkState

    override fun create(): DataSource<String, Member> = pagedListDataSource

}