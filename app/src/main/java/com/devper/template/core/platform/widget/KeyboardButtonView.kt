package com.devper.template.core.platform.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import com.devper.template.R
import com.devper.template.databinding.ViewKeyboardButtonBinding

class KeyboardButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseViewGroup(context, attrs, defStyleAttr) {

    private lateinit var binding: ViewKeyboardButtonBinding

    private var text: String? = null
    private var image: Drawable? = null
    private var rippleEnabled: Boolean = true

    override fun bindView() {
        binding = ViewKeyboardButtonBinding.inflate(LayoutInflater.from(context), this, false).also {
            addView(it.root)
        }
    }

    override fun getAttribute(attrs: AttributeSet?){
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.KeyboardButtonView, 0, 0)
        text = attributes.getString(R.styleable.KeyboardButtonView_lp_keyboard_button_text)
        image = attributes.getDrawable(R.styleable.KeyboardButtonView_lp_keyboard_button_image)
        rippleEnabled = attributes.getBoolean(R.styleable.KeyboardButtonView_lp_keyboard_button_ripple_enabled, true)
    }

    private fun initializeView() {
        if (text != null) {
            binding.tvKeyboardButton.text = text
        }
        if (image != null) {
            binding.imgKeyboardButton.setImageDrawable(image)
            binding.imgKeyboardButton.visibility = View.VISIBLE
        }
        if (!rippleEnabled) {
            binding.root.visibility = View.INVISIBLE
        } else {
            binding.root.visibility = View.VISIBLE
        }
    }

    override fun setupView() {
        initializeView()
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        onTouchEvent(event)
        return false
    }

}