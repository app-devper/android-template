package com.devper.template.core.platform.fcm

import android.content.Context
import me.leolin.shortcutbadger.ShortcutBadger

class BadgeHelper(private val context: Context) {

    private var sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)

    var badgeCount: Int
        get() = sharedPreferences.getInt(BADGE_COUNT_KEY, 0)
        set(badgeCount) {
            storeBadgeCount(badgeCount)
            ShortcutBadger.applyCount(context, badgeCount)
        }

    private fun storeBadgeCount(badgeCount: Int) {
        val editor = sharedPreferences.edit()
        editor?.let {
            editor.putInt(BADGE_COUNT_KEY, badgeCount)
            editor.apply()
        }
    }

    companion object {
        private const val PREFERENCES_FILE = "BadgeCountFile"
        private const val BADGE_COUNT_KEY = "BadgeCount"
    }
}
