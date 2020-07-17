package com.devper.template.presentation.main

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.devper.template.core.platform.widget.ConfirmDialog

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
        withTitle(title)
        withDescription(message)
        withConfirm {

        }
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
        withTitle(title)
        withDescription(message)
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




