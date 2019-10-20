package com.devper.template.core.ext

fun Double.to2Digits(): String {
    return String.format("%,.2f", this)
}