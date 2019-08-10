package com.devper.template

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.SparseArray
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.devper.common.api.Resource
import com.devper.common.api.Status
import com.devper.template.common.util.ServiceHelper
import com.devper.template.widget.ConfirmDialog

var progress: Dialog? = null

fun initProgress(dialog: Dialog) {
    progress = dialog
}

fun <T> AppCompatActivity.handlerResponse(response: Resource<T>?): T? {
    hideLoading()
    if (response == null) {
        return null
    }
    return when (response.status) {
        Status.SUCCESS -> {
            response.data
        }
        Status.ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.LOADING -> {
            showLoading()
            response.data
        }
        Status.TIMEOUT -> {
            showMessage(response.message)
            response.data
        }
        Status.CONVERTION_ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.OTHER_ERROR -> {
            showMessage(response.message)
            response.data
        }
    }
}

fun <T> AppCompatActivity.handlerResponseOnly(response: Resource<T>?): T? {
    if (response == null) {
        return null
    }
    return when (response.status) {
        Status.SUCCESS -> {
            response.data
        }
        Status.ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.LOADING -> {
            response.data
        }
        Status.TIMEOUT -> {
            showMessage(response.message)
            response.data
        }
        Status.CONVERTION_ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.OTHER_ERROR -> {
            showMessage(response.message)
            response.data
        }
    }
}

fun Fragment.appCompat(): AppCompatActivity {
    return activity as AppCompatActivity
}

fun <T> Fragment.handlerResponseOnly(response: Resource<T>?): T? {
    return appCompat().handlerResponseOnly(response)
}

fun <T> Fragment.handlerResponse(response: Resource<T>?): T? {
    hideLoading()
    return appCompat().handlerResponse(response)
}

fun showLoading() {
    progress?.let {
        if (!it.isShowing) {
            it.show()
        }
    }
}

fun hideLoading() {
    progress?.let {
        if (it.isShowing) {
            it.dismiss()
        }
    }
}

fun AppCompatActivity.showMessage(message: String?) {
    showMessageTag(message?: "Error", "dialog")
}

fun AppCompatActivity.showConfirmMessage(title: String, message: String, tag: String) {
    val fragment = ConfirmDialog.Builder().setTitle(title).setMessage(message).setPositive(android.R.string.yes).setNegative(android.R.string.no).build()
    fragment.show(supportFragmentManager, tag)
}

fun AppCompatActivity.showMessageTag(message: String, tag: String) {
    showMessageTagTitle("Warning", message, tag)
}

fun AppCompatActivity.showMessageTagTitle(title: String, message: String, tag: String) {
    val fragment = ConfirmDialog.Builder().setTitle(title).setMessage(message).setPositive(android.R.string.ok).build()
    fragment.show(supportFragmentManager, tag)
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
