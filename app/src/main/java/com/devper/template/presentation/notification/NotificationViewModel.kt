package com.devper.template.presentation.notification

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devper.template.domain.model.notification.Notification
import com.devper.template.domain.usecase.notification.GetNotificationsUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.Flow

class NotificationViewModel @ViewModelInject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : BaseViewModel() {

    val adapter = NotificationPageAdapter()

    private var currentSearchResult: Flow<PagingData<Notification>>? = null

    fun getNotifications(): Flow<PagingData<Notification>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Notification>> = getNotificationsUseCase(1)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}