package com.devper.template.core.extension

import android.content.res.Resources

fun Double.to2Digits(): String {
    return String.format("%,.2f", this)
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()