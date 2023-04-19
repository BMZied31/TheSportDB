package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceDecisionMaker @Inject constructor(
    private val checkConnection: CheckConnection,
    private val syncStatusManager: SyncStatusManager
) {
    fun shouldFetchFromRemote(): Boolean {
        return checkConnection.isConnected() && syncStatusManager.shouldSync()
    }
}
