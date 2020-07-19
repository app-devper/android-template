package com.devper.template.core.extension

import java.text.SimpleDateFormat
import java.util.*

const val SERVER_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS"
const val SERVER_DATE_PATTERN = "yyyy-MM-dd"

const val PRESENT_DATE_PATTERN = "d MMM yyyy"
const val PRESENT_TIME_PATTERN = "HH:mm"

fun String.toDisplayDateBc(pattern: String = SERVER_DATE_TIME_PATTERN): String {
    return toDateBc(this, pattern, PRESENT_DATE_PATTERN)
}

fun String.toDisplayDate(pattern: String = SERVER_DATE_PATTERN): String {
    return toDateBc(this, pattern, PRESENT_DATE_PATTERN)
}

fun String.toWrapDateTime(): String = "${this.toDisplayDateBc()} (${this.toDisplayTime()} น.)"

fun String.toDisplayTime(): String? {
    return toDateBc(this, SERVER_DATE_TIME_PATTERN, PRESENT_TIME_PATTERN)
}

private fun toDateBc(date: String, inFormat: String, outFormat: String): String {
    val calendar = Calendar.getInstance()
        .apply {
            time = SimpleDateFormat(inFormat, Locale.getDefault()).run {
                timeZone = TimeZone.getTimeZone("UTC")
                parse(date) ?: return date
            }
        }
    return SimpleDateFormat(outFormat, Locale("th")).run {
        format(calendar.time)
    }
}