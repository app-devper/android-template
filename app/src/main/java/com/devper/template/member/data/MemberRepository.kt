package com.devper.template.member.data

import androidx.paging.toLiveData
import com.devper.common.networkThread
import com.devper.template.app.db.AppDatabase
import com.devper.template.app.api.AppService
import com.devper.template.app.model.PagedListResult
import com.devper.template.member.model.Member

class MemberRepository(val db: AppDatabase, private val service: AppService) {

    fun getMemberPaging(): PagedListResult<Member> {
        val dataSourceFactory = PagedListDataSourceFactory(service)
        val result = dataSourceFactory.toLiveData(pageSize = 20, fetchExecutor = networkThread())
        return PagedListResult(result, dataSourceFactory.isInitialLoading, dataSourceFactory.networkState)
    }

}