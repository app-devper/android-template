package com.devper.template.presentation.user

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.devper.template.core.platform.NetworkState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.user.GetUsersUseCase

class UserDataSourceFactory(useCase: GetUsersUseCase) : DataSource.Factory<Int, User>() {

    private var pagedListDataSource = UserDataSource(useCase)

    val isInitialLoading: LiveData<Boolean> = pagedListDataSource.isInitialLoading

    val networkState: LiveData<NetworkState> = pagedListDataSource.networkState

    override fun create(): DataSource<Int, User> = pagedListDataSource

}