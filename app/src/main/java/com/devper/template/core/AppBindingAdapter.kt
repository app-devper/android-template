package com.devper.template.core

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

object AppBindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.loadImage(url: String?) {
       this.load(url)
    }

}