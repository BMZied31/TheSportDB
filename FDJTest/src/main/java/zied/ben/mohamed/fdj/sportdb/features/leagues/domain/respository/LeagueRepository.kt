package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository

import kotlinx.coroutines.flow.Flow
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel

/**
 * This interface defines the contract for retrieving the list of leagues.
 */
interface LeagueRepository {

    /**
     * Fetches a list of leagues.
     *
     * @return a flow of [FDJResult] which contains a list [LeagueModel]
     */
    suspend fun getLeagues(): Flow<FDJResult<List<LeagueModel>>>
}
