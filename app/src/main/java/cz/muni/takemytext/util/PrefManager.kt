package cz.muni.takemytext.util

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    companion object {
        private const val PREF_NAME = "prefs_take_my_text"

        private const val LAST_APP_START_MILLIS = "last_app_start_millis"
    }

    private val shared: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var lastAppStartDate: Long
        get() = shared.getLong(LAST_APP_START_MILLIS, 0)
        set(value) = shared.edit().putLong(LAST_APP_START_MILLIS, value).apply()
}