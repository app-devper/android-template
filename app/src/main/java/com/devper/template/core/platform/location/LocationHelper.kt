package  com.devper.template.core.platform.location

import android.content.Context

internal object LocationHelper {
    private const val PREF_APP = "prefs"
    private const val KEY_REQUESTING_LOCATION_UPDATES = "location_updates"

    fun requestingLocationUpdates(context: Context): Boolean {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
            .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false)
    }

    fun setRequestingLocationUpdates(context: Context, requestingLocationUpdates: Boolean) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
            .apply()
    }
}
