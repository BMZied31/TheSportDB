package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A singleton class responsible for accessing shared preferences for this app.
 *
 * @property sharedPreferences The shared preferences object to access.
 */
@Singleton
class SharedPreferencesAccessor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        // The key for the last sync timestamp of leagues.
        private const val LEAGUE_LAST_SYNC_TIMESTAMP_KEY = "league_last_sync_timestamp"

        // The key for the last sync timestamp of teams.
        private const val TEAM_LAST_SYNC_TIMESTAMP_KEY = "team_last_sync_timestamp"

        // The key for the set of league names with saved teams.
        private const val LEAGUE_NAMES_WITH_SAVED_TEAMS = "league_names_with_saved_teams"
    }

    /**
     * Gets or sets the timestamp of the last sync of leagues.
     */
    var leagueLastSyncTimestamp: Long
        get() = sharedPreferences.getLong(LEAGUE_LAST_SYNC_TIMESTAMP_KEY, 0)
        set(value) = sharedPreferences.edit().putLong(LEAGUE_LAST_SYNC_TIMESTAMP_KEY, value).apply()

    /**
     * Gets or sets the timestamp of the last sync of teams.
     */
    var teamLastSyncTimestamp: Long
        get() = sharedPreferences.getLong(TEAM_LAST_SYNC_TIMESTAMP_KEY, 0)
        set(value) = sharedPreferences.edit().putLong(TEAM_LAST_SYNC_TIMESTAMP_KEY, value).apply()

    /**
     * Gets or sets the set of league names with saved teams.
     */
    var leagueNamesWithSavedTeams: List<String>
        get() = (sharedPreferences.getStringSet(LEAGUE_NAMES_WITH_SAVED_TEAMS, emptySet()) ?: emptySet()).toList()
        set(value) {
            val currentSet = leagueNamesWithSavedTeams.toMutableSet()
            currentSet.addAll(value)
            sharedPreferences.edit().putStringSet(LEAGUE_NAMES_WITH_SAVED_TEAMS, currentSet).apply()
        }
}
