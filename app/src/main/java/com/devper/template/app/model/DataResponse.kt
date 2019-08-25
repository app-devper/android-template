package com.devper.template.app.model

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("data")
    val data: T?
) : BaseResponse()