package com.devper.template.domain.core.thread

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineThreadDispatcher {
    fun ui(): CoroutineDispatcher
    fun compute(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}