package com.devper.common

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

private val DISK_IO_EXECUTOR = Executors.newSingleThreadExecutor()
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
private val NETWORK_EXECUTOR = Executors.newFixedThreadPool(3)
private val MAIN_EXECUTOR = MainThreadExecutor()

fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

fun diskThread(f: () -> Unit) {
    DISK_IO_EXECUTOR.execute(f)
}

fun networkThread(f: () -> Unit) {
    NETWORK_EXECUTOR.execute(f)
}

fun mainThread(f: () -> Unit) {
    MAIN_EXECUTOR.execute(f)
}

fun networkThread(): Executor {
    return NETWORK_EXECUTOR
}

fun ioThread(): Executor {
    return IO_EXECUTOR
}

fun diskThread(): Executor {
    return DISK_IO_EXECUTOR
}

private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}
