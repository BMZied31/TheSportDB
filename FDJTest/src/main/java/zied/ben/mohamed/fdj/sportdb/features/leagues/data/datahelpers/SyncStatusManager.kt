package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers

import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A class that manages the synchronization status for leagues and teams.
 *
 * @property sharedPreferencesAccessor is used to access the shared preferences.
 */
@Singleton
class SyncStatusManager @Inject constructor(
    private val sharedPreferencesAccessor: SharedPreferencesAccessor
) {

    companion object {
        private val SYNC_INTERVAL = TimeUnit.DAYS.toMillis(30)
    }

    /**
     * Returns whether leagues should be synchronized from the remote server or not.
     *
     * @return `true` if leagues should be synchronized, `false` otherwise.
     */
    fun shouldSyncLeagues(): Boolean {
        return shouldSync(sharedPreferencesAccessor.leagueLastSyncTimestamp) {
            sharedPreferencesAccessor.leagueLastSyncTimestamp = it
        }
    }

    /**
     * Returns whether teams for a given league should be synchronized from the remote server or not.
     *
     * @param leagueName The name of the league for which teams should be synchronized.
     * @return `true` if teams should be synchronized, `false` otherwise.
     */
    fun shouldSyncTeams(leagueName: String): Boolean {
        return !isLeagueHasSavedTeams(leagueName) || shouldSync(sharedPreferencesAccessor.teamLastSyncTimestamp) {
            sharedPreferencesAccessor.teamLastSyncTimestamp = it
        }
    }

    /**
     * Returns whether a synchronization is needed based on the last synchronization timestamp and the synchronization interval.
     *
     * @param lastSyncTimestamp The last synchronization timestamp.
     * @param setLastSyncTimestamp A function to set the last synchronization timestamp.
     * @return `true` if a synchronization is needed, `false` otherwise.
     */
    private fun shouldSync(lastSyncTimestamp: Long, setLastSyncTimestamp: (Long) -> Unit): Boolean {
        return try {
            val currentTimeStamp = System.currentTimeMillis()
            val isSyncNeeded = currentTimeStamp - lastSyncTimestamp > SYNC_INTERVAL
            if (isSyncNeeded) {
                setLastSyncTimestamp(currentTimeStamp)
            }
            isSyncNeeded
        } catch (e: Exception) {
            Timber.e("Couldn't determine if shouldSync. $e")
            false
        }
    }

    /**
     * Checks if the given league name has already been synced with the remote to retrieve its teams.
     * If it's not saved yet, saves it to the shared preferences.
     *
     * @param leagueName The name of the league to check if it has saved teams.
     *
     * @return true if the league has saved teams, false otherwise.
     */
    private fun isLeagueHasSavedTeams(leagueName: String): Boolean {
        if (!sharedPreferencesAccessor.leagueNamesWithSavedTeams.contains(leagueName)) {
            sharedPreferencesAccessor.leagueNamesWithSavedTeams = listOf(leagueName)
            return false
        }
        return true
    }
}
