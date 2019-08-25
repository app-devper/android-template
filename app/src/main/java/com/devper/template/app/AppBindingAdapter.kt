package com.devper.template.app

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object AppBindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.loadImage(url: String?) {
        url?.let {
            Glide.with(context).load(it).into(this)
        }
    }

}