package com.devper.template.core.thread

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    fun ui(): CoroutineDispatcher
    fun compute(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}