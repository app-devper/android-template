package com.devper.template.domain.usecase.auth

import com.devper.template.data.preference.PreferenceStorage
import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.auth.PinParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyPinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository,
    private val pref: PreferenceStorage
) : FlowUseCase<PinParam, Verify>(dispatcher.io()) {

    override fun execute(params: PinParam): Flow<ResultState<Verify>> {
        return flow {
            emit(ResultState.Loading())
            val verify = repo.verifyPin(params)
            pref.pin = params.pin
            emit(ResultState.Success(verify))
        }
    }
}