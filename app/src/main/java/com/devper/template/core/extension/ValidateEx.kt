package com.devper.template.core.extension

import android.text.TextUtils

fun String.isPinSensitive(): Boolean {
    return when {
        validateCase1(this) -> {
            true
        }
        validateCase2(this) -> {
            true
        }
        validateCase3(this) -> {
            true
        }
        validateCase4(this) -> {
            true
        }
        validateCase5(this) -> {
            true
        }
        validateCase6(this) -> {
            true
        }
        else -> {
            false
        }
    }
}

fun String.isCitizenId(): Boolean {
    val text = this.toNoFormat()
    var check = false
    if (text.length == 13) {
        check = checkCitizenId(text)
    }
    return this.isNotEmpty() && check
}

fun String.isMobileNo(): Boolean {
    val text = this.toNoFormat()
    return this.isNotEmpty() && text.length == 10 && text.startsWith("0")
}

fun String.isTelNo(): Boolean {
    val text = this.toNoFormat()
    return this.isEmpty() || (this.isNotEmpty() && text.length == 9) && text.startsWith("0")
}

fun String.isEmail(): Boolean {
    return this.isEmpty() || (this.isNotEmpty() && validateEmailRegex(this))
}

fun String.isLaserCode(): Boolean {
    val regex = """[a-zA-Z]{2}\d{10}"""
    return this.toNoFormat().matches(regex.toRegex())
}

private fun validateEmailRegex(email: String): Boolean {
    val emailFormat = """^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$"""
    val regex = emailFormat.toRegex()
    return regex.matches(email)
}

private fun checkCitizenId(nid: String): Boolean {
    if (TextUtils.isEmpty(nid)) return true
    var ch = false
    var sum = 0
    if (nid.length == 13) {
        for (i in 0..11) {
            sum += nid[i].toString().toInt() * (13 - i)
        }
        if ((11 - sum % 11) % 10 == nid[12].toString().toInt()) {
            ch = true
        }
    }
    return ch
}

private fun validateCase1(pin: String): Boolean {
    val regex1 = "1234567890"
    val regex2 = "0987654321"
    return regex1.contains(pin, false) || regex2.contains(pin, false)
}

private fun validateCase2(pin: String): Boolean {
    val regex2 = "^(?=([0-9]))\\1{6}".toRegex()
    return regex2.matches(pin)
}

private fun validateCase3(pin: String): Boolean {
    return pin.substring(0, 3) == pin.substring(3, 6) && validateCase1(pin.substring(0, 3))
}

private fun validateCase4(pin: String): Boolean {
    val regex4 = "^(\\d{3})\\1+\$".toRegex()
    return regex4.matches(pin)
}

private fun validateCase5(pin: String): Boolean {
    val regex5 = "^(\\d{2})\\1+\$".toRegex()
    return regex5.matches(pin)
}

private fun validateCase6(pin: String): Boolean {
    val regex6 = "^(?:(\\d)\\1(?!\\1)){2}(\\d)\\2\$".toRegex()
    val pattern = pin.substring(0, 1) + pin.substring(2, 3) + pin.substring(4, 5)
    return regex6.matches(pin) && validateCase1(pattern)
}

