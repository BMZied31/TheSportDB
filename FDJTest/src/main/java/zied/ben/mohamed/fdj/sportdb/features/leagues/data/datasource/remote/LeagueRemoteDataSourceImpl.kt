package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import zied.ben.mohamed.fdj.sportdb.di.IoDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.service.LeaguesApi
import javax.inject.Inject

class LeagueRemoteDataSourceImpl @Inject constructor(
    private val leaguesApi: LeaguesApi,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : LeagueRemoteDataSource {

    override suspend fun getLeagues(): LeagueWrapperResponse =
        withContext(dispatcherIO) {
            leaguesApi.getLeagues()
        }
}
