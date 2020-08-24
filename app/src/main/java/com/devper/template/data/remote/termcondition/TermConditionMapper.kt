package com.devper.template.data.remote.termcondition

import com.devper.template.domain.model.termcondition.TermCondition

class TermConditionMapper {

    fun toDomain(it: TermConditionData): TermCondition {
        return TermCondition(it.id, it.content, it.version, it.createdDate)
    }


}