package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamsWrapperResponse

/**
 * This interface defines the contract for retrieving the list of teams from api.
 */
interface TeamRemoteDataSource {

    /**
     * Fetches a list of teams from given league.
     *
     * @param leagueName the name of the league which we want to retrieve its teams
     *
     * @return [LeagueWrapperResponse] object containing the raw leagues data.
     */
    suspend fun getTeamsByLeague(leagueName: String): TeamsWrapperResponse
}
