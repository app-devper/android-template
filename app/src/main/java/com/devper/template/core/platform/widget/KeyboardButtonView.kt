package com.devper.template.core.platform.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.devper.template.R

class KeyboardButtonView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(mContext, attrs, defStyleAttr) {

    private fun initializeView(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val attributes = mContext.theme.obtainStyledAttributes(attrs, R.styleable.KeyboardButtonView, defStyleAttr, 0)
            val text = attributes.getString(R.styleable.KeyboardButtonView_lp_keyboard_button_text)
            val image = attributes.getDrawable(R.styleable.KeyboardButtonView_lp_keyboard_button_image)
            val rippleEnabled = attributes.getBoolean(R.styleable.KeyboardButtonView_lp_keyboard_button_ripple_enabled, true)
            attributes.recycle()
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.view_keyboard_button, this) as KeyboardButtonView
            if (text != null) {
                val textView = view.findViewById<TextView>(R.id.keyboard_button_textview)
                textView.text = text
            }
            if (image != null) {
                val imageView = view.findViewById<AppCompatImageView>(R.id.keyboard_button_imageview)
                imageView.setImageDrawable(image)
                imageView.visibility = View.VISIBLE
            }
            if (!rippleEnabled) {
                view.visibility = View.INVISIBLE
            } else {
                view.visibility = View.VISIBLE
            }
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        onTouchEvent(event)
        return false
    }

    init {
        initializeView(attrs, defStyleAttr)
    }
}