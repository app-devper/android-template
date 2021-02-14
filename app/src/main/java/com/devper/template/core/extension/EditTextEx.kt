package com.devper.template.core.extension

import android.view.View
import android.widget.EditText
import com.devper.template.core.extension.helper.DecimalWatcher
import com.devper.template.core.extension.helper.MaskWatcher

fun EditText.toText() = this.text.toString()

fun EditText.toDecimalFormat(format: String = "#,###,##0.00", digits: Int = 2) {
    val watcher = DecimalWatcher(this, format, digits)
    this.addTextChangedListener(watcher)
}

fun EditText.toLaserCode() {
    val watcher = MaskWatcher(this, "###-#######-####")
    this.addTextChangedListener(watcher)
}

fun EditText.toTelNo() {
    val watcher = MaskWatcher(this, "##-###-####")
    this.addTextChangedListener(watcher)
}

fun EditText.toBankAcctNo() {
    val watcher = MaskWatcher(this, "###-#-#####-#")
    this.addTextChangedListener(watcher)
}

fun EditText.toMobileNo() {
    val watcher = MaskWatcher(this, "###-###-####")
    this.addTextChangedListener(watcher)
}

fun EditText.toCitizenId() {
    val watcher = MaskWatcher(this, "#-####-#####-##-#")
    this.addTextChangedListener(watcher)
}

fun EditText.validateWhiteSpace() {
    var text = this.text.toString()
    if (text.contains(" ")) {
        text = text.replace("""\s""".toRegex(), "")
        this.setText(text)
        this.setSelection(text.length)
    }
}

fun EditText.toDecimalInput(format: String = "#,###,##0.00", digits: Int = 2) {
    val watcher = DecimalWatcher(this, format, digits)
    this.addTextChangedListener(watcher)
    this.onFocusChange { b ->
        val payAmount = this.toText().currencyToDouble()
        if (!b) {
            if (payAmount == 0.0) {
                this.setText("")
            } else {
                this.setText(payAmount.to2Digits())
            }
        } else {
            if (payAmount == 0.0) {
                this.setText("")
            } else {
                if (payAmount.isInteger()) {
                    this.setText(payAmount.toNoDigits())
                } else {
                    this.setText(payAmount.to2Digits())
                }
            }
        }
    }
}

inline fun EditText.onFocusChange(crossinline hasFocus: (Boolean) -> Unit) {
    onFocusChangeListener = View.OnFocusChangeListener { _, b -> hasFocus(b) }
}




