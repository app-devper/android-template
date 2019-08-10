package com.devper.template.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView

import com.wang.avi.AVLoadingIndicatorView
import com.devper.template.R

class ProgressHudDialog : Dialog {
    constructor(context: Context) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val avi = findViewById<AVLoadingIndicatorView>(R.id.avi)
        avi.show()
    }

    fun setMessage(message: CharSequence?) {

        if (message != null && message.isNotEmpty()) {
            findViewById<View>(R.id.message).visibility = View.VISIBLE
            val txt = findViewById<View>(R.id.message) as TextView
            txt.text = message
            txt.invalidate()
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
            try {
                dialog.setTitle("")
                dialog.setContentView(R.layout.dialog_progress_hud)
                if (message == null || message.isEmpty()) {
                    dialog.findViewById<View>(R.id.message).visibility = View.GONE
                } else {
                    val txt = dialog.findViewById<View>(R.id.message) as TextView
                    txt.text = message
                }
                dialog.setCancelable(cancelable)
                dialog.window!!.attributes.gravity = Gravity.CENTER
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.2f
                dialog.window!!.attributes = lp
                return dialog
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dialog
        }
    }
}
