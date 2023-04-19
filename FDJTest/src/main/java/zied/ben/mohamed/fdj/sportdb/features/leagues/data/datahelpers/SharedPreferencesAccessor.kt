package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesAccessor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val LAST_SYNC_TIMESTAMP_KEY = "last_sync_timestamp"
    }

    var lastSyncTimestamp: Long
        get() = sharedPreferences.getLong(LAST_SYNC_TIMESTAMP_KEY, 0)
        set(value) = sharedPreferences.edit().putLong(LAST_SYNC_TIMESTAMP_KEY, value).apply()
}
