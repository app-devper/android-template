package com.devper.template.core.platform.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.devper.template.R

class KeyboardView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(mContext, attrs, defStyleAttr), View.OnClickListener {

    var onClick: (item: KeyboardButtonEnum) -> Unit = {}

    private var mButtons: MutableList<KeyboardButtonView> = mutableListOf()

    private fun initializeView(attrs: AttributeSet?, defStyleAttr: Int) {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_keyboard, this) as KeyboardView
        initKeyboardButtons(view)
    }

    private fun initKeyboardButtons(view: KeyboardView) {
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_0) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_1) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_2) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_3) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_4) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_5) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_6) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_7) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_8) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_9) as KeyboardButtonView)
        mButtons.add(view.findViewById<View>(R.id.pin_code_button_clear) as KeyboardButtonView)
        for (button in mButtons) {
            button.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.pin_code_button_0 -> {
                onClick(KeyboardButtonEnum.BUTTON_0)
            }
            R.id.pin_code_button_1 -> {
                onClick(KeyboardButtonEnum.BUTTON_1)
            }
            R.id.pin_code_button_2 -> {
                onClick(KeyboardButtonEnum.BUTTON_2)
            }
            R.id.pin_code_button_3 -> {
                onClick(KeyboardButtonEnum.BUTTON_3)
            }
            R.id.pin_code_button_4 -> {
                onClick(KeyboardButtonEnum.BUTTON_4)
            }
            R.id.pin_code_button_5 -> {
                onClick(KeyboardButtonEnum.BUTTON_5)
            }
            R.id.pin_code_button_6 -> {
                onClick(KeyboardButtonEnum.BUTTON_6)
            }
            R.id.pin_code_button_7 -> {
                onClick(KeyboardButtonEnum.BUTTON_7)
            }
            R.id.pin_code_button_8 -> {
                onClick(KeyboardButtonEnum.BUTTON_8)
            }
            R.id.pin_code_button_9 -> {
                onClick(KeyboardButtonEnum.BUTTON_9)
            }
            R.id.pin_code_button_clear -> {
                onClick(KeyboardButtonEnum.BUTTON_CLEAR)
            }
        }
    }

    init {
        initializeView(attrs, defStyleAttr)
    }
}