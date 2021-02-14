package com.devper.template.domain.repository

import com.devper.template.domain.model.termcondition.TermCondition

interface TermConditionRepository {

    suspend fun getTermCondition(): TermCondition

}