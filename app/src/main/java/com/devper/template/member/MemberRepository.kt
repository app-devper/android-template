package com.devper.template.member

import androidx.paging.toLiveData
import com.devper.common.networkThread
import com.devper.template.common.AppDatabase
import com.devper.template.common.AppService
import com.devper.template.common.model.PagedListResult
import com.devper.template.member.data.PagedListDataSourceFactory
import com.devper.template.member.model.Member

class MemberRepository(val db: AppDatabase, val service: AppService) {

    fun getMemberPaging(): PagedListResult<Member> {
        val dataSourceFactory = PagedListDataSourceFactory(service)
        val result = dataSourceFactory.toLiveData(pageSize = 20, fetchExecutor = networkThread())
        return PagedListResult(result, dataSourceFactory.isInitialLoading, dataSourceFactory.networkState)
    }

}