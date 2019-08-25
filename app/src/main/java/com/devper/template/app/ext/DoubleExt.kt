package com.devper.template.app.ext

fun Double.to2Digits(): String {
    return String.format("%,.2f", this)
}