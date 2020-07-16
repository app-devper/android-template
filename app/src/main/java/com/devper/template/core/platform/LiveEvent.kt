package com.devper.template.core.platform

import androidx.lifecycle.LiveData

abstract class LiveEvent<T> : LiveData<Event<T>> {
    constructor() : super()
    constructor(value: T) : super(Event(value))

    val peekContent: T?
        get() = value?.peekContent()
}

class MutableLiveEvent<T> : LiveEvent<T> {
    constructor() : super()
    constructor(value: T) : super(value)

    fun setEventValue(value: T) {
        setValue(Event(value))
    }

    fun postEventValue(value: T) {
        postValue(Event(value))
    }
}