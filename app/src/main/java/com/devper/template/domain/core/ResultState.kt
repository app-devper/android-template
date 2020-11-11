package com.devper.template.domain.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.devper.template.core.exception.AppException

sealed class ResultState<out T> {

    /**
     * A state of [data] which shows that we know there is still an update to come.
     */
    data class Loading<T>(val data: T? = null) : ResultState<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<out T>(val data: T) : ResultState<T>()

    /**
     * A state to show a [throwable] is thrown.
     */
    data class Error(val throwable: Throwable) : ResultState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$throwable]"
            is Loading -> "Loading"
        }
    }
}

val ResultState<*>.succeeded
    get() = this is ResultState.Success && data != null

inline fun <reified T> ResultState<T>.success(): T? {
    return when (this) {
        is ResultState.Success -> data
        else -> null
    }
}

fun <T> ResultState<T>.successOr(fallback: T): T {
    return (this as? ResultState.Success<T>)?.data ?: fallback
}

fun Throwable.toError(): AppException {
    return ErrorMapper.toAppError(this)
}

val <T> ResultState<T>.data: T?
    get() = (this as? ResultState.Success)?.data

inline fun <reified T> ResultState<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is ResultState.Success) {
        liveData.value = data
    }
}

inline fun <reified T> LiveData<ResultState<T>>.asLiveData(): LiveData<T> {
    return liveData {
        this@asLiveData.value?.success()?.let {
            emit(it)
        }
    }
}
