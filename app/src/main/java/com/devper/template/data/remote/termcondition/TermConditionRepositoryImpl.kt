package com.devper.template.data.remote.termcondition

import com.devper.template.data.remote.ApiService
import com.devper.template.domain.model.termcondition.TermCondition
import com.devper.template.domain.repository.TermConditionRepository
import javax.inject.Inject

class TermConditionRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : TermConditionRepository {
    override suspend fun getTermCondition(): TermCondition {
        val mapper = TermConditionMapper()
        return api.getTermCondition().let {
            mapper.toDomain(it)
        }
    }
}