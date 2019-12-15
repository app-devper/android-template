package com.devper.template.presentation

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