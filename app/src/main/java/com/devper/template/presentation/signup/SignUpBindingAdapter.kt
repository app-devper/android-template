package com.devper.template.presentation.signup

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.devper.template.R
import com.google.android.material.textfield.TextInputLayout

object SignUpBindingAdapter {

    @BindingAdapter(value = ["signup", "error"], requireAll = true)
    @JvmStatic
    fun signUpWatcher(textInputLayout: TextInputLayout, loginMsg: String?, errorMsg: String) {
        textInputLayout.editText?.let {
            if (loginMsg == "signup" && it.text.isNullOrEmpty()) {
                it.background = ContextCompat.getDrawable(it.context, R.drawable.all_input_error_bg)
                textInputLayout.error = errorMsg
            } else {
                it.background = ContextCompat.getDrawable(it.context, R.drawable.all_input_bg)
                textInputLayout.error = null
            }
            it.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    it.background = ContextCompat.getDrawable(it.context, R.drawable.all_input_bg)
                    textInputLayout.error = null
                }
            })
        }
    }
}