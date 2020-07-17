package com.devper.template.presentation.user

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.core.platform.NetworkState
import com.devper.template.core.platform.PagedListResult
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.user.GetUsersUseCase
import com.devper.template.presentation.BaseViewModel
import java.util.concurrent.Executors

class UserViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    val adapter = UserAdapter()
    private val pagedListResult: MutableLiveData<PagedListResult<User>> = MutableLiveData()

    val userList: LiveData<PagedList<User>> = Transformations.switchMap(pagedListResult) { it.result }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(pagedListResult) { it.networkState }
    val isInitialLoading: LiveData<Boolean> = Transformations.switchMap(pagedListResult) { it.isInitialLoading }

    fun getUsers() {
        val dataSourceFactory = UserDataSourceFactory(getUsersUseCase)
        val result = dataSourceFactory.toLiveData(pageSize = 10, fetchExecutor = Executors.newFixedThreadPool(3))
        pagedListResult.value = PagedListResult(result, dataSourceFactory.isInitialLoading, dataSourceFactory.networkState)
    }

    fun nextToDetail(id: String) {
        val bundle = bundleOf(
            AppConfig.EXTRA_PARAM to id
        )
        onNavigate(R.id.user_to_user_detail, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        getUsersUseCase.unsubscribe()
    }

}
