package com.devper.template.domain.usecase.notification

import androidx.paging.*
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.notification.Notification
import com.devper.template.domain.repository.NotificationRepository
import com.devper.template.domain.usecase.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: NotificationRepository
) : PagingUseCase<Int, PagingData<Notification>>(dispatcher.io()) {

    class NotificationPagingSource(
        private val repository: NotificationRepository
    ) : PagingSource<Int, Notification>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
            return try {
                repository.getNotifications(params.key ?: 1).run {
                    val nextKey = if (params.key == totalPages) null else params.key?.plus(1)
                    LoadResult.Page(results, null, nextKey)
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, Notification>): Int {
            return 1
        }
    }

    override fun execute(params: Int): Flow<PagingData<Notification>> {
        return Pager(PagingConfig(10), 1) {
            NotificationPagingSource(repo)
        }.flow
    }
}