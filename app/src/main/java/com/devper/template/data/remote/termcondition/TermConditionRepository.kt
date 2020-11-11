package com.devper.template.data.remote.termcondition

import com.devper.template.domain.model.termcondition.TermCondition

interface TermConditionRepository {

    suspend fun getTermCondition(): TermCondition

}