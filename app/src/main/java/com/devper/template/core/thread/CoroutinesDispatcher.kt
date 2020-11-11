package com.devper.template.core.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutinesDispatcher : Dispatcher {

    override fun ui(): CoroutineDispatcher = Dispatchers.Main

    override fun compute(): CoroutineDispatcher = Dispatchers.Default

    override fun io(): CoroutineDispatcher = Dispatchers.IO
}