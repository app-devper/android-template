package com.devper.template.core.platform.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.devper.template.R
import java.util.*

class PinCodeRoundView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(mContext, attrs, defStyleAttr) {

    private var mRoundViews: MutableList<ImageView> = mutableListOf()

    private var maxPin = 6
    var pin = ""
        private set
    var currentLength = 0
        private set
    var onSuccess: (item: String) -> Unit = {}
    private var mEmptyDotDrawableId: Drawable? = null
    private var mFullDotDrawableId: Drawable? = null
    private lateinit var mRoundContainer: ViewGroup

    private fun initializeView(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val attributes = mContext.theme.obtainStyledAttributes(attrs, R.styleable.PinCodeView, defStyleAttr, 0)
            mEmptyDotDrawableId = attributes.getDrawable(R.styleable.PinCodeView_lp_empty_pin_dot)
            if (mEmptyDotDrawableId == null) {
                mEmptyDotDrawableId = resources.getDrawable(R.drawable.pin_round_empty, null)
            }
            mFullDotDrawableId = attributes.getDrawable(R.styleable.PinCodeView_lp_full_pin_dot)
            if (mFullDotDrawableId == null) {
                mFullDotDrawableId = resources.getDrawable(R.drawable.pin_round_full, null)
            }
            attributes.recycle()
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.view_round_pin_code, this) as PinCodeRoundView
            mRoundContainer = view.findViewById<View>(R.id.round_container) as ViewGroup
            mRoundViews = ArrayList()
            setPinLength(maxPin)
        }
    }

    private fun refresh(pinLength: Int) {
        currentLength = pinLength
        for (i in mRoundViews.indices) {
            if (pinLength - 1 >= i) {
                mRoundViews[i].setImageDrawable(mFullDotDrawableId)
            } else {
                mRoundViews[i].setImageDrawable(mEmptyDotDrawableId)
            }
        }
    }

    fun setEmptyDotDrawable(drawable: Drawable?) {
        mEmptyDotDrawableId = drawable
    }

    fun setFullDotDrawable(drawable: Drawable?) {
        mFullDotDrawableId = drawable
    }

    fun setEmptyDotDrawable(drawableId: Int) {
        mEmptyDotDrawableId = resources.getDrawable(drawableId)
    }

    fun setFullDotDrawable(drawableId: Int) {
        mFullDotDrawableId = resources.getDrawable(drawableId)
    }

    fun setPin(value: KeyboardButtonEnum) {
        if (value.buttonValue != -1) {
            if (pin.length == maxPin) {
                return
            }
            pin += value.buttonValue
            if (pin.length == maxPin) {
                onSuccess(pin)
            }
        } else {
            if (pin.isNotEmpty()) {
                pin = pin.substring(0, pin.length - 1)
            }
        }
        refresh(pin.length)
    }

    fun clearPin(){
        pin = ""
        refresh(pin.length)
    }

    private fun setPinLength(pinLength: Int) {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mRoundContainer.removeAllViews()
        val temp: MutableList<ImageView> = ArrayList(pinLength)
        for (i in 0 until pinLength) {
            val roundView: ImageView = if (i < mRoundViews.size) {
                mRoundViews[i]
            } else {
                inflater.inflate(R.layout.view_round, mRoundContainer, false) as ImageView
            }
            mRoundContainer.addView(roundView)
            temp.add(roundView)
        }
        mRoundViews.clear()
        mRoundViews.addAll(temp)
        refresh(0)
    }

    init {
        initializeView(attrs, defStyleAttr)
    }
}