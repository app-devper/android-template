package com.devper.template.presentation.main

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.devper.template.core.widget.ConfirmDialog
import com.devper.template.domain.core.ErrorMapper

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
    val errorMapper = ErrorMapper()
    val error = errorMapper.toErrorException(throwable)
    appCompat().showMessageTag(error.resultCode, error.getDesc())
}

