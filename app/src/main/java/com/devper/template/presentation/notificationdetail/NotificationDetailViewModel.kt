package com.devper.template.presentation.notificationdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.notification.Notification
import com.devper.template.domain.model.notification.NotificationParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.notification.GetNotificationUseCase
import com.devper.template.domain.usecase.notification.GetNotificationsUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationDetailViewModel @Inject constructor(
    private val getNotificationUseCase: GetNotificationUseCase
) : BaseViewModel() {

    private var _notification: MutableLiveData<Notification> = MutableLiveData()
    val notification: LiveData<Notification> = _notification
    val notificationResult = SingleLiveEvent<ResultState<Notification>>()

    fun getNotification(id: String) {
        getNotificationUseCase(NotificationParam(id))
            .onEach { notificationResult.value = it }
            .launchIn(viewModelScope)
    }

    fun setNotification(it: Notification) {
        _notification.value = it
    }

}
