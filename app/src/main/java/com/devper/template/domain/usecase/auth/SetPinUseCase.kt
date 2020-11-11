package com.devper.template.domain.usecase.auth

import com.devper.template.data.preference.PreferenceStorage
import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.auth.SetPinParam
import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetPinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository,
    private val pref: PreferenceStorage
) : FlowUseCase<SetPinParam, Unit>(dispatcher.io()) {

    override fun execute(params: SetPinParam): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            repo.setPin(params)
            pref.pin = params.pin
            emit(ResultState.Success(Unit))
        }
    }
}