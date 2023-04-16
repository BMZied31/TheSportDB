package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse

interface LeagueRemoteDataSource {

    suspend fun getLeagues(): LeagueWrapperResponse
}
