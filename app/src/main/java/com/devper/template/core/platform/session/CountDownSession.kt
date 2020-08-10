package com.devper.template.core.platform.session

import android.os.CountDownTimer
import timber.log.Timber
import javax.inject.Inject

class CountDownSession @Inject constructor(private val timeSession: Long) {

    private var timer: CountDownTimer? = null
    var onFinish: () -> Unit = {}
    var isForeground: Boolean = false
    var isFinish: Boolean = false

    fun start() {
        isFinish = false
        timer?.cancel()
        timer = object : CountDownTimer(timeSession, 10000L) {
            override fun onFinish() {
                isFinish = true
                if (isForeground) {
                    onFinish.invoke()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                Timber.d("onTrick :%s", millisUntilFinished)
            }
        }.start()
    }

    fun clear() {
        isFinish = false
        timer?.cancel()
        timer = null
    }

}
