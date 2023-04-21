package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamsWrapperResponse

interface TeamRemoteDataSource {

    suspend fun getTeamsByLeague(leagueName: String): TeamsWrapperResponse
}
