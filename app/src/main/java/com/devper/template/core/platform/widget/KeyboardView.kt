package com.devper.template.core.platform.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.devper.template.R
import com.devper.template.databinding.ViewKeyboardBinding

class KeyboardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : BaseViewGroup(context, attrs, defStyleAttr, defStyleRes), View.OnClickListener {

    private lateinit var binding: ViewKeyboardBinding

    override fun bindView() {
        binding = ViewKeyboardBinding.inflate(LayoutInflater.from(context), this, false).also {
            addView(it.root)
        }
    }

    var onClick: (item: KeyboardButtonEnum) -> Unit = {}

    var onOther: () -> Unit = {}

    private fun initKeyboardButtons() {
        binding.pinCodeButton0.setOnClickListener(this)
        binding.pinCodeButton1.setOnClickListener(this)
        binding.pinCodeButton2.setOnClickListener(this)
        binding.pinCodeButton3.setOnClickListener(this)
        binding.pinCodeButton4.setOnClickListener(this)
        binding.pinCodeButton5.setOnClickListener(this)
        binding.pinCodeButton6.setOnClickListener(this)
        binding.pinCodeButton7.setOnClickListener(this)
        binding.pinCodeButton8.setOnClickListener(this)
        binding.pinCodeButton9.setOnClickListener(this)
        binding.pinCodeButton10.setOnClickListener(this)
        binding.pinCodeButtonClear.setOnClickListener(this)
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
            R.id.pin_code_button_10 -> {
                onOther()
            }
            R.id.pin_code_button_clear -> {
                onClick(KeyboardButtonEnum.BUTTON_CLEAR)
            }
        }
    }

    override fun setupView() {
        initKeyboardButtons()
    }
}