package com.devper.template.member

import androidx.lifecycle.LiveData
import androidx.paging.toLiveData
import com.devper.common.api.NetworkBoundResource
import com.devper.common.api.Resource
import com.devper.common.networkThread
import com.devper.common.toLiveData
import com.devper.template.common.util.ServiceHelper
import com.devper.template.common.AppDatabase
import com.devper.template.common.model.PagedListResult
import com.devper.template.member.data.PagedListDataSourceFactory
import com.devper.template.member.model.Member
import com.devper.template.member.model.MemberResponse

class MemberRepository(val db: AppDatabase, val service: MemberService) {

    fun getMember(): LiveData<Resource<List<Member>>> {
        return object : NetworkBoundResource<List<Member>, MemberResponse>() {
            override fun shouldFetch(data: List<Member>?) = true
            override fun loadFromDb() = db.member().getMember()
            override fun createCall() = service.members(ServiceHelper.headers).toLiveData()
            override fun saveCallResult(item: MemberResponse) {
                item.data?.results?.let {
                    db.member().insert(it)
                }
            }
        }.asLiveData()
    }

    fun getMemberPaging(): PagedListResult<Member> {
        val dataSourceFactory = PagedListDataSourceFactory(ServiceHelper.headers, service)
        val result = dataSourceFactory.toLiveData(pageSize = 20, fetchExecutor = networkThread())
        return PagedListResult(result, dataSourceFactory.isInitialLoading, dataSourceFactory.networkState)
    }

}