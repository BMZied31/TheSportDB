package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.service.LeaguesApi
import javax.inject.Inject

/**
 * Implementation of the [LeagueRemoteDataSource] interface that interacts with
 * the [LeaguesApi] to fetch the leagues data.
 *
 * @property leaguesApi The instance of [LeaguesApi] to make network calls.
 */
class LeagueRemoteDataSourceImpl @Inject constructor(
    private val leaguesApi: LeaguesApi
) : LeagueRemoteDataSource {

    /**
     * Calls [leaguesApi] to fetch the leagues data.
     *
     * @return A [LeagueWrapperResponse] object containing the raw leagues data.
     */
    override suspend fun getLeagues(): LeagueWrapperResponse =
        leaguesApi.getLeagues()
}
