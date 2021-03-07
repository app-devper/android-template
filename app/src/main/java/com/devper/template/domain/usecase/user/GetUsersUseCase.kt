package com.devper.template.domain.usecase.user

import androidx.paging.*
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.user.User
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: UserRepository
) : PagingUseCase<Int, PagingData<User>>(dispatcher.io()) {

    class UserPagingSource(
        private val repository: UserRepository
    ) : PagingSource<Int, User>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
            return try {
                repository.getUsers(params.key ?: 1).run {
                    val nextKey = if (params.key == totalPages) null else params.key?.plus(1)
                    LoadResult.Page(results, null, nextKey)
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

         override fun getRefreshKey(state: PagingState<Int, User>): Int {
           return 1
         }
     }

    override fun execute(params: Int): Flow<PagingData<User>> {
        return Pager(PagingConfig(10), 1) {
            UserPagingSource(repo)
        }.flow
    }

}