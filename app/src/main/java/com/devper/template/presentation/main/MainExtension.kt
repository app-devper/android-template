package com.devper.template.presentation.main

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.devper.template.core.exception.AppException
import com.devper.template.core.platform.ErrorCode
import com.devper.template.core.widget.ConfirmDialog
import com.devper.template.domain.core.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun MainActivity.showLoading() {
    progress.let {
        if (!it.isShowing) {
            it.show()
        }
    }
}

fun MainActivity.hideLoading() {
    progress.let {
        if (it.isShowing) {
            it.dismiss()
        }
    }
}

fun MainActivity.showMessage(message: String?) {
    showMessageTag("dialog", message)
}

fun MainActivity.showConfirmMessage(title: String, message: String, tag: String) {
    val fragment = ConfirmDialog.Builder().run {
        setTitle(title)
        setMessage(message)
        setPositive(android.R.string.yes)
        setNegative(android.R.string.no)
        build()
    }
    fragment.show(supportFragmentManager, tag)
}

fun MainActivity.showMessageTag(tag: String, message: String?) {
    showMessageTagTitle("Warning", message, tag)
}

fun MainActivity.showMessageTagTitle(title: String, message: String?, tag: String) {
    if (message == null) {
        return
    }
    val fragment = ConfirmDialog.Builder().run {
        setTitle(title)
        setMessage(message)
        setPositive(android.R.string.ok)
        build()
    }
    fragment.show(supportFragmentManager, tag)
}

fun MainActivity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.appCompat(): MainActivity {
    return activity as MainActivity
}

fun Fragment.showLoading() {
    appCompat().showLoading()
}

fun Fragment.hideLoading() {
    appCompat().hideLoading()
}

fun Fragment.toError(throwable: Throwable?) {
    throwable?.let {
        val appError = toAppError(throwable)
        appCompat().showMessageTag(appError.resultCode, appError.getDesc())
    }
}

private fun toAppError(throwable: Throwable): AppException {
    return when (throwable) {
        is AppException -> {
            throwable
        }
        is HttpException -> {
            try {
                val result = throwable.response()?.errorBody()?.string()
                val response = Gson().fromJson(result, ErrorResponse::class.java)
                AppException(response.resCode, response.resMessage)
            } catch (e: Exception) {
                e.printStackTrace()
                AppException(ErrorCode.ERROR.name, e.message ?: "")
            }
        }
        is CancellationException -> {
            AppException(ErrorCode.CANCEL.name, throwable.message ?: "")
        }
        is SocketTimeoutException -> {
            AppException(ErrorCode.TIMEOUT.name, throwable.message ?: "")
        }
        is IOException -> {
            AppException(ErrorCode.CONVERSION_ERROR.name, throwable.message ?: "")
        }
        is UnknownHostException -> {
            AppException(ErrorCode.CONVERSION_ERROR.name, throwable.message ?: "")
        }
        else -> {
            AppException(ErrorCode.OTHER_ERROR.name, throwable.message ?: "")
        }
    }
}
