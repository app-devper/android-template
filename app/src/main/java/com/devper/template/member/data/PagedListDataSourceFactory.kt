package com.devper.template.member.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.devper.template.common.util.NetworkState
import com.devper.template.member.MemberService
import com.devper.template.member.model.Member

class PagedListDataSourceFactory(headers: Map<String, String>, api: MemberService) : DataSource.Factory<String, Member>() {

    private var pagedListDataSource = PagedListDataSource(headers, api)

    val isInitialLoading: LiveData<Boolean> = pagedListDataSource.isInitialLoading

    val networkState: LiveData<NetworkState> = pagedListDataSource.networkState

    override fun create(): DataSource<String, Member> = pagedListDataSource

}