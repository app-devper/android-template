package com.devper.template.presentation.user

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.user.GetUsersUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    val adapter = UserPageAdapter()

    private var currentSearchResult: Flow<PagingData<User>>? = null

    fun getUsers(): Flow<PagingData<User>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<User>> = getUsersUseCase(1).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun nextToDetail(id: String) {
        val bundle = bundleOf(
            AppConfig.EXTRA_PARAM to id
        )
        onNavigate(R.id.user_to_user_detail, bundle)
    }

}
