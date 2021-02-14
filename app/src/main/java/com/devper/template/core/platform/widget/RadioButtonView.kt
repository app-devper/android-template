package com.devper.template.core.platform.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.devper.template.R
import com.devper.template.core.extension.toGone
import com.devper.template.core.extension.toVisible
import com.devper.template.databinding.ViewRadioButtonBinding
import com.google.android.material.card.MaterialCardView

class RadioButtonView constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context) : this(context, null, 0)

    private var isActive = false

    private var binding: ViewRadioButtonBinding = ViewRadioButtonBinding.inflate(LayoutInflater.from(context), this, false).also {
        addView(it.root)
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.RadioButtonView, 0, 0)

        binding.tvTitle.text = typedArray.getString(R.styleable.RadioButtonView_title)
        binding.tvDetail.text = typedArray.getString(R.styleable.RadioButtonView_detail)
        displayDetailByMsg(binding.tvDetail.text.toString())

        setupView()
        setupStyle()
    }

    var title: CharSequence
        get() = binding.tvTitle.text
        set(value) {
            binding.tvTitle.text = value
        }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    var detail: CharSequence
        get() = binding.tvDetail.text
        set(value) {
            binding.tvDetail.text = value
            displayDetailByMsg(value.toString())
        }

    private fun displayDetailByMsg(msg: String) {
        if (msg == "") {
            binding.tvDetail.toGone()
        } else {
            binding.tvDetail.toVisible()
        }
    }

    var isCheck: Boolean
        get() = isActive
        set(value) {
            isActive = value
            displayRadioByStatus()
        }

    private fun setupStyle() {
        this.strokeWidth = 1
        this.elevation = 3.0F
    }

    private fun setupView() {
        this.isClickable = true
        this.radius = 10.0F
        displayRadioByStatus()
    }

    private fun displayRadioByStatus() {
        if (this.isCheck) {
            displayChecked()
        } else {
            displayUnChecked()
        }
    }

    private fun displayUnChecked() {
        this.strokeColor = ContextCompat.getColor(context, R.color.gray)
        binding.radioButton.setImageResource(R.drawable.ic_radio_inactive)
    }

    private fun displayChecked() {
        this.strokeColor = ContextCompat.getColor(context, R.color.blue)
        binding.radioButton.setImageResource(R.drawable.ic_radio_active)
    }

}