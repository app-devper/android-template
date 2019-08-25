package com.devper.template.app.ext

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.LayerDrawable
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.devper.template.R
import com.devper.template.app.widget.CountDrawable

fun TextView.applyColor(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
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

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.toVisibleOrGone(isGone: Boolean) {
    when {
        isGone -> toGone()
        else -> toVisible()
    }
}

fun View.toEnable() {
    this.isEnabled = true
}

fun View.toDisable() {
    this.isEnabled = false
}

fun View.toEnableOrDisable(isEnableView: Boolean) {
    this.isEnabled = isEnableView
}

fun EditText.toText() = this.text.toString()

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

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