package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers

import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncStatusManager @Inject constructor(
    private val sharedPreferencesAccessor: SharedPreferencesAccessor
) {

    companion object {
        private val SYNC_INTERVAL = TimeUnit.DAYS.toMillis(1)
    }

    fun shouldSync(): Boolean {
        return try {
            val lastSyncTimestamp = sharedPreferencesAccessor.lastSyncTimestamp
            val currentTimeStamp = System.currentTimeMillis()
            val isSyncNeeded = currentTimeStamp - lastSyncTimestamp > SYNC_INTERVAL
            if (isSyncNeeded) {
                updateSyncStatus(currentTimeStamp)
            }
            isSyncNeeded
        } catch (e: Exception) {
            Timber.e("Couldn't determine if shouldSync. $e")
            false
        }
    }

    private fun updateSyncStatus(currentTimeStamp: Long) {
        sharedPreferencesAccessor.lastSyncTimestamp = currentTimeStamp
    }
}
