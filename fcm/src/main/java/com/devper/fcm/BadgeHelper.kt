package com.devper.fcm

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import me.leolin.shortcutbadger.ShortcutBadger

class BadgeHelper(private val mContext: Context) {
    private var sharedPreferences: SharedPreferences

    var badgeCount: Int
        get() = sharedPreferences.getInt(BADGE_COUNT_KEY, 0)
        set(badgeCount) {
            storeBadgeCount(badgeCount)
            if (badgeCount == 0) {
                ShortcutBadger.removeCount(mContext)
                Log.d(TAG, "Remove count")
            } else {
                ShortcutBadger.applyCount(mContext, badgeCount)
                Log.d(TAG, "Apply count: $badgeCount")
            }
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
