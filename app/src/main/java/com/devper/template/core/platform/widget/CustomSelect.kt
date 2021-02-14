package com.devper.template.core.platform.widget

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.devper.template.R
import com.devper.template.core.extension.toDisable
import com.devper.template.core.extension.toEnable
import com.devper.template.core.extension.toGone
import com.devper.template.databinding.CustomViewSelectBinding

class CustomSelect @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : BaseViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var binding: CustomViewSelectBinding

    private var hint: String? = null

    override fun getAttribute(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.DslSelect, 0, 0)
        hint = typedArray.getString(R.styleable.DslSelect_value)
    }

    override fun bindView() {
        binding = CustomViewSelectBinding.inflate(LayoutInflater.from(context), this, false).also {
            addView(it.root)
        }
    }

    override fun setupView() {
        binding.tvTitle.text = hint ?: context.getString(R.string.please_select)
    }

    fun setEnable(isEnable: Boolean) {
        if (isEnable) {
            this.toEnable()
            binding.layoutSelect.toEnable()
        } else {
            this.toDisable()
            binding.layoutSelect.toDisable()
        }
    }

    fun setValue(value: String?) {
        binding.tvTitle.text = value ?: this.hint ?: context.getString(R.string.please_select)
    }

    fun setValue(@StringRes id: Int) {
        binding.tvTitle.setText(id)
    }

    fun clearValue() {
        binding.tvTitle.text = hint ?: context.getString(R.string.please_select)
    }

    fun clearError() {
        binding.tvTitle.text = hint ?: context.getString(R.string.please_select)
        binding.tvSelectError.toGone()
        binding.layoutSelect.setBackgroundResource(R.drawable.selector_stroke_border)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return superState?.let {
            val ss = onSaveChildInstanceState(SavedState(superState)) as SavedState
            ss
        }
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state)
    }

    internal class SavedState : ChildSavedState {

        constructor(superState: Parcelable) : super(superState)

        constructor(parcel: Parcel, classLoader: ClassLoader?) : super(parcel, classLoader) {
            // save data here
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            // restore data here
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.ClassLoaderCreator<SavedState> = object : Parcelable.ClassLoaderCreator<SavedState> {

                override fun createFromParcel(source: Parcel, loader: ClassLoader): SavedState {
                    return SavedState(source, loader)
                }

                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source, null)
                }

                override fun newArray(size: Int): Array<SavedState> {
                    return newArray(size)
                }
            }
        }
    }

}