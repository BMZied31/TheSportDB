package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDao
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity
import javax.inject.Inject

/**
 * Implementation of [LeagueLocalDataSource] that uses a Room database to store data locally.
 *
 * @property leagueDao The DAO for accessing the league data.
 */
class LeagueLocalDataSourceImpl @Inject constructor(
    private val leagueDao: LeagueDao
) : LeagueLocalDataSource {

    /**
     * Retrieves all leagues from the database.
     *
     * @return A list of [LeagueEntity] objects.
     */
    override suspend fun getAllLeagues(): List<LeagueEntity> =
        leagueDao.getAllLeagues()

    /**
     * Inserts a list of leagues into the database.
     *
     * @param list The list of [LeagueEntity] objects to insert.
     */
    override suspend fun insertAllLeagues(list: List<LeagueEntity>) {
        leagueDao.insertAllLeagues(leaguesList = list)
    }

    /**
     * Deletes all leagues from the database.
     */
    override suspend fun deleteAll() {
        leagueDao.deleteAllLeagues()
    }
}
