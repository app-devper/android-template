package com.devper.template.presentation.notification

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.domain.model.notification.Notification
import com.devper.template.domain.usecase.notification.GetNotificationsUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
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

    fun nextToDetail(id: String) {
        val bundle = bundleOf(
            AppConfig.EXTRA_PARAM to id
        )
        onNavigate(R.id.notification_to_notification_detail, bundle)
    }

}
