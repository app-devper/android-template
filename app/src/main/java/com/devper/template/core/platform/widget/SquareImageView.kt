package com.devper.template.core.platform.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class SquareImageView : AppCompatImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth: Int = this.measuredWidth
        val measuredHeight: Int = this.measuredHeight
        if (measuredHeight > measuredWidth) {
            this.setMeasuredDimension(measuredWidth, measuredWidth)
        } else {
            this.setMeasuredDimension(measuredHeight, measuredHeight)
        }
    }
}