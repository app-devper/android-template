package com.devper.template.core.platform.widget

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.devper.template.R
import com.devper.template.databinding.DialogProgressBinding

class ProgressDialog(private val context: Context) {

    val binding: DialogProgressBinding = DialogProgressBinding.inflate(LayoutInflater.from(context))

    private val loadingDialog: Dialog by lazy {
        val dialog = Dialog(context, R.style.AppTheme_ProgressBar_White).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCancelable(false)
        }
        dialog
    }

    fun isLoading(): Boolean = loadingDialog.isShowing

    fun showLoading() {
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
        }
    }

    fun setTextLoading(message: String) {
        binding.loadingText.text = message
    }

    fun dismissLoading() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
}