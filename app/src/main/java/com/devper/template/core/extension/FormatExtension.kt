package com.devper.template.core.extension

import java.math.BigInteger
import java.security.MessageDigest
import java.text.DecimalFormat

fun String.toPureNumber(): Double = replace(",".toRegex(), "").toDoubleOrNull() ?: 0.0

fun String.toCurrency(): String  =  String.format("%,.2f", toPureNumber())

fun String.changeFormat(): String {
    if (this.isEmpty()) {
        return "0"
    }
    val number = java.lang.Double.parseDouble(this.replace(",", ""))
    val format = DecimalFormat("###,###,###,###,###")
    return format.format(number)
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun String.currencyToDouble(): Double = replace(",".toRegex(), "").toDoubleOrNull() ?: 0.0

fun String.toCurrencyNoDigit(): String  =  String.format("%,.0f", currencyToDouble())

fun String.toIntNoComma(): Int = this.replace(",", "").toInt()

fun String.toNoComma(): String = this.replace(",", "")

fun String.toStringNoFormat(): String = this.replace("-", "")
