package com.devper.template.domain.core

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

    /**
     * A state which show when we end of [stream] or grouping onError or onNext into only emit
     */
    class Finally<T>(val data: T? = null, val throwable: Throwable? = null) : ResultState<T>()
}