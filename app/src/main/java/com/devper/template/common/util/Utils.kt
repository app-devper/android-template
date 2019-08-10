package com.devper.template.common.util

import java.math.BigInteger
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.changeFormat(): String {
    if (this.isEmpty()) {
        return "0"
    }
    val number = java.lang.Double.parseDouble(this.replace(",", ""))
    val format = DecimalFormat("###,###,###,###,###")
    return format.format(number)
}

fun requestFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd 00:00:00.000", Locale.getDefault())
    return format.format(Date())
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}