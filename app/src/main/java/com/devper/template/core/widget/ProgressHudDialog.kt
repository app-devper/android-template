package com.devper.template.core.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.devper.template.R
import kotlinx.android.synthetic.main.dialog_loading.*

class ProgressHudDialog(context: Context, theme: Int) : Dialog(context, theme) {

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        avi.show()
    }

    fun setMessage(message: CharSequence?) {
        if (message != null && message.isNotEmpty()) {
            tv_message.visibility = View.VISIBLE
            tv_message.text = message
            tv_message.invalidate()
        } else {
            tv_message.visibility = View.GONE
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            super.hide()
            e.printStackTrace()
        }
    }

    companion object {

        fun init(context: Context, message: CharSequence?, cancelable: Boolean): ProgressHudDialog {
            val dialog = ProgressHudDialog(context, R.style.ProgressHUD)
            dialog.setTitle("")
            dialog.setContentView(R.layout.dialog_loading)
            dialog.setMessage(message)
            dialog.setCancelable(cancelable)
            dialog.window?.attributes?.gravity = Gravity.CENTER
            val lp = dialog.window?.attributes
            lp?.dimAmount = 0.2f
            dialog.window?.attributes = lp
            return dialog
        }
    }
}
