package com.devper.template.core.extension

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.devper.template.R

private val patternScore = listOf(
    """(?=.*[a-zA-Z0-9]).{8,}""",
    """((?=.*[a-z][A-Z])|(?=.*[A-Z][a-z])|(?=.*[0-9][a-z])|(?=.*[a-z][0-9])|(?=.*[A-Z][0-9])|(?=.*[0-9][A-Z])).{8,}""",
    """(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z]).{8,}""",
    """(?=.*[\!\@\#\$\%\^\&\*\(\)\-\_\=\+\|\[\]\{\}\;\:\\\/\?\.\>\<]).{8,}"""
)

enum class PasswordScore(val minScore: Int, val maxScore: Int, val progress: Int, @ColorRes val color: Int, @StringRes val message: Int, val isShowPasswordScore: Int, val isShowRemark: Int) {
    DEFAULT(0, 0, 0, R.color.colorGreyLighter, R.string.change_password_password_score_simple, View.GONE, View.VISIBLE),
    SIMPLE(1, 1, 33, R.color.colorRed, R.string.change_password_password_score_simple, View.VISIBLE, View.VISIBLE),
    MIDDLE(2, 3, 66, R.color.colorYellowLight, R.string.change_password_password_score_middle, View.VISIBLE, View.GONE),
    HARD(4, 4, 100, R.color.colorGreenLight, R.string.change_password_password_score_hard, View.VISIBLE, View.GONE)
}

private fun validateMatchPasswordPattern(password: String): Int {
    var score = 0
    patternScore.forEach { if (password.matches(it.toRegex())) score += 1 }
    return score
}

fun String.getPasswordScore(): PasswordScore {
    return when (validateMatchPasswordPattern(this)) {
        in PasswordScore.SIMPLE.minScore..PasswordScore.SIMPLE.maxScore -> PasswordScore.SIMPLE
        in PasswordScore.MIDDLE.minScore..PasswordScore.MIDDLE.maxScore -> PasswordScore.MIDDLE
        in PasswordScore.HARD.minScore..PasswordScore.HARD.maxScore -> PasswordScore.HARD
        else -> PasswordScore.DEFAULT
    }
}



