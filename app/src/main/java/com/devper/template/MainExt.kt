package com.devper.template

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.devper.common.api.Resource
import com.devper.common.api.Status
import com.devper.template.app.ext.parse
import com.devper.template.app.model.BaseResponse
import com.devper.template.app.widget.ConfirmDialog

fun <T> MainActivity.handlerResponse(resource: Resource<T>?, isLoading: Boolean = true): T? {
    hideLoading()
    if (resource == null) {
        return null
    }
    return when (resource.status) {
        Status.SUCCESS -> {
            resource.data
        }
        Status.ERROR -> {
            val response = resource.message?.parse<BaseResponse>()
            showMessageTag(response?.resMessage ?: resource.message, response?.resCode ?: "")
            resource.data
        }
        Status.LOADING -> {
            if (isLoading) {
                showLoading()
            }
            resource.data
        }
        Status.TIMEOUT -> {
            showMessage(resource.message)
            resource.data
        }
        Status.CONVERSION_ERROR -> {
            showMessage(resource.message)
            resource.data
        }
        Status.OTHER_ERROR -> {
            showMessage(resource.message)
            resource.data
        }
    }
}

fun Fragment.appCompat(): MainActivity {
    return activity as MainActivity
}

fun <T> Fragment.handlerResponse(resource: Resource<T>?, isLoading: Boolean = true): T? {
    return appCompat().handlerResponse(resource, isLoading)
}

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

fun Fragment.showLoading() {
    appCompat().showLoading()
}

fun Fragment.hideLoading() {
    appCompat().hideLoading()
}

fun MainActivity.showMessage(message: String?) {
    showMessageTag(message, "dialog")
}

fun MainActivity.showConfirmMessage(title: String, message: String, tag: String) {
    val fragment = ConfirmDialog.Builder().setTitle(title).setMessage(message).setPositive(android.R.string.yes).setNegative(android.R.string.no).build()
    fragment.show(supportFragmentManager, tag)
}

fun MainActivity.showMessageTag(message: String?, tag: String) {
    showMessageTagTitle("Warning", message, tag)
}

fun MainActivity.showMessageTagTitle(title: String, message: String?, tag: String) {
    if (message == null) {
        return
    }
    val fragment = ConfirmDialog.Builder().setTitle(title).setMessage(message).setPositive(android.R.string.ok).build()
    fragment.show(supportFragmentManager, tag)
}

fun MainActivity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
