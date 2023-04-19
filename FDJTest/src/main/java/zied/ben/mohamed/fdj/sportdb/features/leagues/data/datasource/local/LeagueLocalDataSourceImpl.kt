package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import zied.ben.mohamed.fdj.sportdb.di.IoDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDao
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity
import javax.inject.Inject

class LeagueLocalDataSourceImpl @Inject constructor(
    private val leagueDao: LeagueDao,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : LeagueLocalDataSource {

    override suspend fun getAllLeagues(): List<LeagueEntity>? =
        withContext(dispatcherIO) {
            leagueDao.getAll()
        }

    override suspend fun insertAllLeagues(list: List<LeagueEntity>) {
        withContext(dispatcherIO) {
            leagueDao.insertAll(leaguesList = list)
        }
    }

    override suspend fun deleteAll() {
        withContext(dispatcherIO) {
            leagueDao.deleteAll()
        }
    }
}
