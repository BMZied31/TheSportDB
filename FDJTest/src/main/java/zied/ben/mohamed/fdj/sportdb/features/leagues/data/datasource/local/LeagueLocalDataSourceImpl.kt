package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDao
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity
import javax.inject.Inject

class LeagueLocalDataSourceImpl @Inject constructor(
    private val leagueDao: LeagueDao
) : LeagueLocalDataSource {

    override suspend fun getAllLeagues(): List<LeagueEntity>? =
        leagueDao.getAll()

    override suspend fun insertAllLeagues(list: List<LeagueEntity>) {
        leagueDao.insertAll(leaguesList = list)
    }

    override suspend fun deleteAll() {
        leagueDao.deleteAll()
    }
}
