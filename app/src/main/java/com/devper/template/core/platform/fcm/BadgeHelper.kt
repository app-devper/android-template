package com.devper.template.core.platform.fcm

import android.content.Context
import android.content.SharedPreferences
import me.leolin.shortcutbadger.ShortcutBadger

class BadgeHelper(private val mContext: Context) {

    private var sharedPreferences: SharedPreferences

    var badgeCount: Int
        get() = sharedPreferences.getInt(BADGE_COUNT_KEY, 0)
        set(badgeCount) {
            storeBadgeCount(badgeCount)
            ShortcutBadger.applyCount(mContext, badgeCount)
        }

    init {
        sharedPreferences = mContext.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    private fun storeBadgeCount(badgeCount: Int) {
        val editor = sharedPreferences.edit()
        editor?.let {
            editor.putInt(BADGE_COUNT_KEY, badgeCount)
            editor.apply()
        }
    }

    companion object {
        private const val TAG = "BadgeHelper"
        private const val PREFERENCES_FILE = "BadgeCountFile"
        private const val BADGE_COUNT_KEY = "BadgeCount"
    }
}
