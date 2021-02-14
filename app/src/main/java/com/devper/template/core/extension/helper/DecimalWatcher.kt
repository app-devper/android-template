package com.devper.template.core.extension.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.ParseException

class DecimalWatcher(private val et: EditText, format: String, digits: Int) : TextWatcher {

    private var df: DecimalFormat = DecimalFormat(format)
    private val decimalFormat: DecimalFormat = DecimalFormat("#,###")
    private var hasFractionalPart: Boolean = false
    private var oldString: String? = null
    var maxLength: Int = 7
    private var isNeedDecimal: Boolean

    init {
        df.isDecimalSeparatorAlwaysShown = true
        df.maximumFractionDigits = digits
        hasFractionalPart = false
        isNeedDecimal = false
    }

    override fun afterTextChanged(s: Editable) {
        et.removeTextChangedListener(this)
        decimalControl(s)
        et.addTextChangedListener(this)
    }

    private fun decimalControl(s: Editable) {
        try {
            val initLen: Int = et.text.length
            val v = s.toString().replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")
            if (v.isEmpty()) {
                return
            }
            val n = df.parse(v)
            val cp = et.selectionStart
            if (hasFractionalPart) {
                val decimal = v.substring(0, v.indexOf('.')).length
                if (decimal <= maxLength) {
                    val length = v.substring(v.indexOf('.')).length
                    if (length == 3) {
                        et.setText(df.format(n))
                    } else if (length > 3) {
                        et.setText(oldString)
                    }
                } else {
                    et.setText(oldString)
                }
            } else {
                if (v.length <= maxLength) {
                    et.setText(decimalFormat.format(n))
                } else {
                    et.setText(oldString)
                }
            }
            val endLen: Int = et.text.length
            val sel = cp + (endLen - initLen)
            if (sel > 0 && sel <= et.text.length) {
                et.setSelection(sel)
            } else {
                et.setSelection(et.text.length - 1)
            }
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        } catch (nfe: ParseException) {
            nfe.printStackTrace()
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        oldString = et.text.toString()
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasFractionalPart = s.toString().contains(df.decimalFormatSymbols.decimalSeparator.toString())
    }
}

