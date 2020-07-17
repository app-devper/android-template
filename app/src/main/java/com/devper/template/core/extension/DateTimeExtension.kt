package com.devper.template.core.extension

import java.text.SimpleDateFormat
import java.util.*

const val SERVER_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
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
    val local = Locale("th")
    val calendar = Calendar.getInstance()
        .apply {
            time = SimpleDateFormat(inFormat, local).parse(date) ?: return date
            add(Calendar.YEAR, 543)
        }
    return SimpleDateFormat(outFormat, local).format(calendar.time)
}