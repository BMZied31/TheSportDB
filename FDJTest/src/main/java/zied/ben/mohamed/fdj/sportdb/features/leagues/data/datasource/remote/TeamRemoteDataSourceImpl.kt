package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamsWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.service.LeaguesApi
import javax.inject.Inject

class TeamRemoteDataSourceImpl @Inject constructor(
    private val leaguesApi: LeaguesApi
) : TeamRemoteDataSource {

    override suspend fun getTeamsByLeague(leagueName: String): TeamsWrapperResponse =
        leaguesApi.getTeamsByLeague(leagueName = leagueName)
}
