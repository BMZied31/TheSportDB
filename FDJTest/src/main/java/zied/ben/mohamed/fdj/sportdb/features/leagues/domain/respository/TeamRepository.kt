package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository

import kotlinx.coroutines.flow.Flow
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel

/**
 * This interface defines the contract for retrieving the list of teams by league.
 */
interface TeamRepository {

    /**
     * Fetches a list of teams by league.
     *
     * @return a flow of [FDJResult] which contains a list [TeamModel]
     */
    suspend fun getTeamsByLeague(leagueName: String): Flow<FDJResult<List<TeamModel>>>
}
