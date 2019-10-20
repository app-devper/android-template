package com.devper.template.presentation.movie

import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.databinding.BindingAdapter

object MovieBindingAdapter {

    @BindingAdapter("buttonOnClick")
    @JvmStatic
    fun Button.onClick(url: String?) {
        url?.let { item ->
            setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(item))
                context.startActivity(i)
            }
        }
    }

}