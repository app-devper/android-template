package com.devper.template.member

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.devper.template.common.util.ServiceHelper
import com.devper.template.member.model.Member
import java.util.concurrent.Executor

class MemberBoundaryCallback(
    private val webservice: MemberService, private val ioExecutor: Executor, private val networkPageSize: Int
) : PagedList.BoundaryCallback<Member>() {

    @MainThread
    override fun onZeroItemsLoaded() {
        val queries = HashMap<String, String>()
        queries["offset"] = "0"
        queries["limit"] = networkPageSize.toString()
        webservice.members(ServiceHelper.headers)
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Member) {

    }

    override fun onItemAtFrontLoaded(itemAtFront: Member) {
        // ignored, since we only ever append to what's in the DB
    }

}