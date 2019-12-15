package com.devper.template.core.extension

fun Double.to2Digits(): String {
    return String.format("%,.2f", this)
}
