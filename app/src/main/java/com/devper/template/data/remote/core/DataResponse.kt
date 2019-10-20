package com.devper.template.data.remote.core

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("data")
    val data: T
)