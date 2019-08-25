package com.devper.template.app.util

import com.devper.common.api.ApiResponse
import com.devper.common.api.Resource
import com.devper.template.app.model.DataResponse


fun <T> ApiResponse<DataResponse<T>>.data(): T? {
    return this.body?.data
}

fun <T> ApiResponse<DataResponse<T>>.resource(): Resource<T>? {
    return if (isSuccessful) {
        Resource.success(body?.data)
    } else {
        Resource.error(status, errorMessage ?: "Unknown error", body?.data)
    }
}

