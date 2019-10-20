package com.devper.template.data.repository

import androidx.paging.toLiveData
import com.devper.common.networkThread
import com.devper.template.presentation.member.MemberDataSourceFactory
import com.devper.template.data.remote.AppService
import com.devper.template.presentation.core.PagedListResult
import com.devper.template.domain.model.member.Member

class MemberRepository(private val service: AppService) {

    fun getMemberPaging(): PagedListResult<Member> {
        val dataSourceFactory = MemberDataSourceFactory(service)
        val result = dataSourceFactory.toLiveData(pageSize = 20, fetchExecutor = networkThread())
        return PagedListResult(
            result,
            dataSourceFactory.isInitialLoading,
            dataSourceFactory.networkState
        )
    }

}