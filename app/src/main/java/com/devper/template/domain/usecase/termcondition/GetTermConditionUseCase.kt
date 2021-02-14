package com.devper.template.domain.usecase.termcondition

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.termcondition.TermCondition
import com.devper.template.domain.repository.TermConditionRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTermConditionUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: TermConditionRepository
) : FlowUseCase<Unit, TermCondition>(dispatcher.io()) {

    override fun execute(params: Unit): Flow<ResultState<TermCondition>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.getTermCondition()))
        }
    }
}