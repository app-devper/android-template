package com.devper.template.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.devper.common.api.AbsentLiveData
import com.devper.common.api.Resource
import com.devper.template.common.util.NetworkState
import com.devper.template.common.model.PagedListResult
import com.devper.template.member.model.Member

class MemberViewModel internal constructor(private val repo: MemberRepository) : ViewModel() {

    private var query: MutableLiveData<Map<String, String>> = MutableLiveData()
    private val pagedListResult: MutableLiveData<PagedListResult<Member>> = MutableLiveData()

    val memberList: LiveData<PagedList<Member>> = Transformations.switchMap(pagedListResult) { it.result }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(pagedListResult) { it.networkState }
    val isInitialLoading: LiveData<Boolean> = Transformations.switchMap(pagedListResult) { it.isInitialLoading }

    fun getMember() {
        pagedListResult.value = repo.getMemberPaging()
    }

    fun retry() {
    }
}
