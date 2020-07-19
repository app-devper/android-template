package com.devper.template.core.platform.widget

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.devper.template.R

class CustomTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    var require: Boolean = false

    init {
        setup(attrs)
    }

    private fun setup(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0)
        require = typedArray.getBoolean(R.styleable.CustomTextView_require, false)
        if (require) {
            updateText()
        }
    }

    private fun updateText() {
        try {
            val colored = "*"
            val builder = SpannableStringBuilder()
            builder.append(text)
            builder.append(" ")
            val start: Int = builder.length
            builder.append(colored)
            val end: Int = builder.length
            builder.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorRed)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = builder
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}