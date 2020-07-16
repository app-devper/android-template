package com.devper.template.core.platform.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.devper.template.R
import com.devper.template.core.extension.toGone
import com.devper.template.core.extension.toVisible
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.view_radio_button.view.*

class RadioButtonView constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context) : this(context, null, 0)

    private var isActive = false

    init {
        View.inflate(context, R.layout.view_radio_button, this)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.RadioButtonView, 0, 0)

        tvTitle.text = typedArray.getString(R.styleable.RadioButtonView_title)
        tvDetail.text = typedArray.getString(R.styleable.RadioButtonView_detail)
        displayDetailByMsg(tvDetail.text.toString())

        setupView()
        setupStyle()
    }

    var title: CharSequence
        get() = tvTitle.text
        set(value) {
            tvTitle.text = value
        }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    var detail: CharSequence
        get() = tvDetail.text
        set(value) {
            tvDetail.text = value

            displayDetailByMsg(value.toString())
        }

    private fun displayDetailByMsg(msg: String) {
        if (msg == "") {
            tvDetail.toGone()
        } else {
            tvDetail.toVisible()
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
        radioButton.setImageResource(R.drawable.ic_radio_inactive)
    }

    private fun displayChecked() {
        this.strokeColor = ContextCompat.getColor(context, R.color.blue)
        radioButton.setImageResource(R.drawable.ic_radio_active)
    }

}