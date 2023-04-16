package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.service.LeaguesApi
import javax.inject.Inject

class LeagueRemoteDataSourceImpl @Inject constructor(
    private val leaguesApi: LeaguesApi
) : LeagueRemoteDataSource {

    override suspend fun getLeagues(): LeagueWrapperResponse =
        leaguesApi.getLeagues()
}
