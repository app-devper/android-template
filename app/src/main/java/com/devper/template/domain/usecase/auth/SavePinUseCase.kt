package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.provider.PreferenceProvider
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.auth.PinParam
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavePinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository,
    private val pref: PreferenceProvider
) : FlowUseCase<PinParam, Unit>(dispatcher.io()) {

    override fun execute(params: PinParam): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            val verify = repo.verifyPin(params).also {
                pref.pin = params.pin
            }
            emit(ResultState.Success(Unit))
        }
    }
}