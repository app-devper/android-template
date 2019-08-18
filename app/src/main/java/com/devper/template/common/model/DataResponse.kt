package com.devper.template.common.model

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("data")
    val data: T?
) : BaseResponse()