package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers

import javax.inject.Inject
import javax.inject.Singleton

/**
 * A singleton class that's responsible for making a decision on whether or not to fetch data from the remote
 * data source, based on the network connectivity and sync status.
 *
 * @param checkConnection used to check for network connectivity
 * @param syncStatusManager used to determine if local data is stale or not
 */
@Singleton
class DataSourceDecisionMaker @Inject constructor(
    private val checkConnection: CheckConnection,
    private val syncStatusManager: SyncStatusManager
) {

    /**
     * Determines whether to fetch leagues from remote data source or not.
     * Fetch from remote if the device is connected to the internet and local data is stale.
     *
     * @return true if leagues should be fetched from remote data source, false otherwise.
     */
    fun shouldFetchLeaguesFromRemote(): Boolean {
        return checkConnection.isConnected() && syncStatusManager.shouldSyncLeagues()
    }

    /**
     * Determines whether to fetch teams for a given league from remote data source or not.
     * Fetch from remote if the device is connected to the internet and local data is stale.
     *
     * @param leagueName the name of the league to fetch teams for
     * @return true if teams should be fetched from remote data source, false otherwise.
     */
    fun shouldFetchTeamsFromRemote(leagueName: String): Boolean {
        return checkConnection.isConnected() && syncStatusManager.shouldSyncTeams(leagueName)
    }
}
