package com.devper.template.core.extension

import kotlin.math.ceil
import kotlin.math.floor

fun Double.to2Digits(): String {
    return String.format("%,.2f", this)
}

fun Double.to7Digits(): String {
    return String.format("%,.7f", this)
}

fun Double.toNoDigits(): String {
    return String.format("%,.0f", this)
}

fun Int.toNoDigits(): String {
    return String.format("%,d", this)
}

fun Double.isInteger(): Boolean {
    return ceil(this) == floor(this)
}
