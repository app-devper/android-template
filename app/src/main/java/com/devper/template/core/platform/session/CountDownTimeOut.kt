package com.devper.template.core.platform.session

import android.os.CountDownTimer

class CountDownTimeOut constructor(private val timeSession: Long) {

    var onFinish: () -> Unit = {}

    fun clear() {
        timer?.cancel()
        timer = null
    }

    fun handlerTimeOut(isLogin: Boolean) {
        if (isLogin) {
            timer = object : CountDownTimer(timeSession, 1000L) {
                override fun onFinish() {
                    onFinish.invoke()
                }

                override fun onTick(millisUntilFinished: Long) {}
            }.start()
        }
    }

    companion object {
        private var timer: CountDownTimer? = null
    }

}
