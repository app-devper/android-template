package com.devper.template.common.util

import java.text.SimpleDateFormat
import java.util.*

object ServiceHelper {

    private const val ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

    val headers: HashMap<String, String>
        get() {
            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            headers["x-app-name"] = "Market Place"
            headers["x-transaction-id"] = genTransactionId()
            return headers
        }

    private fun randomAlphaNumeric(count: Int): String {
        var c = count
        val builder = StringBuilder()
        while (c-- != 0) {
            val character = (Math.random() * ALPHA_NUMERIC_STRING.length).toInt()
            builder.append(ALPHA_NUMERIC_STRING[character])
        }
        return builder.toString()
    }

    private fun genTransactionId(): String {
        val format = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault())
        format.format(Date())
        return  format.format(Date()) + "_" + randomAlphaNumeric(5)
    }

}
