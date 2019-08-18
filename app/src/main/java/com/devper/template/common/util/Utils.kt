package com.devper.template.common.util

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.devper.common.api.ApiResponse
import com.devper.common.api.Resource
import com.devper.template.R
import com.devper.template.common.model.DataResponse
import com.google.gson.Gson
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

inline fun <reified T> String.parse(): T? {
    val gson = Gson()
    return gson.fromJson(this, T::class.java)
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan,
            startIndexOfLink,
            startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun <T> ApiResponse<DataResponse<T>>.data(): T? {
    return this.body?.data
}

fun <T> ApiResponse<DataResponse<T>>.resource(): Resource<T>? {
    return if (isSuccessful) {
        Resource.success(body?.data)
    } else {
        Resource.error(status, errorMessage ?: "Unknown error", body?.data)
    }
}

fun MenuItem.setCount(context: Context, count: String) {
    val icon = this.icon as LayerDrawable
    val badge: CountDrawable
    // Reuse drawable if possible
    val reuse = icon.findDrawableByLayerId(R.id.ic_group_count)
    badge = if (reuse != null && reuse is CountDrawable) {
        reuse
    } else {
        CountDrawable(context)
    }
    badge.setCount(count)
    icon.mutate()
    icon.setDrawableByLayerId(R.id.ic_group_count, badge)
}