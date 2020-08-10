package com.devper.template.domain.usecase.device

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.repository.DeviceRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterDeviceUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: DeviceRepository
) : FlowUseCase<Unit, String>(dispatcher.io()) {

    override fun execute(parameters:Unit): Flow<ResultState<String>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.registerDevice()))
        }
    }
}