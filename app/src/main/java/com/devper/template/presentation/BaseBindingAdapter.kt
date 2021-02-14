package com.devper.template.presentation

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devper.template.core.extension.underline

object BaseBindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.loadImage(url: String?) {
        this.load(url)
    }

    @BindingAdapter(value = ["setAdapter"])
    @JvmStatic
    fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>) {
        this.adapter = adapter
    }

    @BindingAdapter("underline")
    @JvmStatic
    fun TextView.under(boolean: Boolean) {
        if (boolean) {
            this.underline()
        }
    }

    @BindingAdapter("applyColor")
    @JvmStatic
    fun TextView.applyColor(@ColorRes color: Int) {
        this.setTextColor(ContextCompat.getColor(context, color))
    }

    @BindingAdapter("applyTint")
    @JvmStatic
    fun ProgressBar.applyTint(@ColorRes color: Int) {
        this.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(context, color))
    }

}