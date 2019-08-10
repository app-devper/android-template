package com.devper.template.login

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.devper.template.R

object LoginBindingAdapter {

    @BindingAdapter(value = ["login", "error"], requireAll = true)
    @JvmStatic
    fun watcher(textInputLayout: TextInputLayout, loginMsg: String?, errorMsg: String) {
        if (loginMsg == "login" && textInputLayout.editText?.text.isNullOrEmpty()) {
            textInputLayout.editText?.background =
                ContextCompat.getDrawable(textInputLayout.editText!!.context, R.drawable.all_input_error_bg)
            textInputLayout.error = errorMsg
        } else {
            textInputLayout.editText?.background =
                ContextCompat.getDrawable(textInputLayout.editText!!.context, R.drawable.all_input_bg)
            textInputLayout.error = null
        }
        textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textInputLayout.editText?.background =
                    ContextCompat.getDrawable(textInputLayout.editText!!.context, R.drawable.all_input_bg)
                textInputLayout.error = null
            }
        })
    }
}