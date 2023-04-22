package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamsWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.service.LeaguesApi
import javax.inject.Inject

/**
 * This class implements the [TeamRemoteDataSource] interface and handles all remote data operations related to teams.
 * It uses the LeaguesApi to get data from the API.
 *
 * @property leaguesApi The instance of [LeaguesApi] to make network calls.
 */
class TeamRemoteDataSourceImpl @Inject constructor(
    private val leaguesApi: LeaguesApi
) : TeamRemoteDataSource {

    /**
     * This function is used to get all teams from the API for a given league.
     *
     * @param leagueName the name of the league for which teams are to be retrieved.
     * @return the response containing the list of teams for the specified league.
     */
    override suspend fun getTeamsByLeague(leagueName: String): TeamsWrapperResponse =
        leaguesApi.getTeamsByLeague(leagueName = leagueName)
}
