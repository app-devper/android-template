package com.devper.template.core.extension

import java.math.BigInteger
import java.security.MessageDigest

fun String.toCurrency(): String = String.format("%,.2f", currencyToDouble())

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun String.currencyToDouble(): Double = toNoComma().toDoubleOrNull() ?: 0.0

fun String.toNoDigit(): String = String.format("%,.0f", currencyToDouble())

fun String.toNoComma(): String = this.replace(",", "")

fun String.toIntNoComma(): Int = this.toNoComma().toInt()

fun String.toNoFormat(): String = this.replace("-", "")
