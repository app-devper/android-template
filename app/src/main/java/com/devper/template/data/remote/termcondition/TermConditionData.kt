package com.devper.template.data.remote.termcondition

import com.google.gson.annotations.SerializedName

data class TermConditionData(
    @SerializedName("_id")
    val id: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("createdDate")
    val createdDate: String,
)