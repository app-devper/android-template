package com.devper.template.domain.usecase.auth

import com.devper.template.AppConfig.LOGIN_ERROR
import com.devper.template.AppConfig.USER_INVALID_ERROR
import com.devper.template.core.exception.AppException
import com.devper.template.data.preference.PreferenceStorage
import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.data.session.AppSessionProvider
import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.auth.LoginParam
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository,
    private val session: AppSessionProvider,
    private val pref: PreferenceStorage
) : FlowUseCase<LoginParam, Unit>(dispatcher.io()) {
    override fun execute(params: LoginParam): Flow<ResultState<Unit>> {
        return flow {
            if (params.username.isNullOrEmpty() || params.password.isNullOrEmpty()) {
                throw AppException(LOGIN_ERROR, "")
            }
            val userId = pref.userId
            if (userId.isNotEmpty()) {
                if (params.username != userId) {
                    throw AppException(USER_INVALID_ERROR, "")
                }
            }
            emit(ResultState.Loading())
            session.accessToken = repo.login(params)
            pref.userId = params.username
            emit(ResultState.Success(Unit))
        }
    }
}