package com.devper.template.core.platform.widget

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.devper.template.R
import com.devper.template.databinding.DialogLoadingBinding

class ProgressHudDialog(context: Context, theme: Int) : AppCompatDialog(context, theme) {

    val binding: DialogLoadingBinding by lazy { DialogLoadingBinding.inflate(layoutInflater) }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        binding.avi.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("")
        setContentView(binding.root)
        setCancelable(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun setMessage(message: CharSequence?) {
        if (message != null && message.isNotEmpty()) {
            binding.tvMessage.visibility = View.VISIBLE
            binding.tvMessage.text = message
            binding.tvMessage.invalidate()
        } else {
            binding.tvMessage.visibility = View.GONE
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
