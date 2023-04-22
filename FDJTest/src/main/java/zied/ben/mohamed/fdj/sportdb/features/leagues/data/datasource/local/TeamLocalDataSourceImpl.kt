package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDao
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.TeamEntity
import javax.inject.Inject

/**
 * Implementation of [TeamLocalDataSource] that uses a Room database to store data locally.
 *
 * @property leagueDao The Data Access Object for accessing the Room database.
 */
class TeamLocalDataSourceImpl @Inject constructor(
    private val leagueDao: LeagueDao
) : TeamLocalDataSource {

    /**
     * Gets all the teams for a given league name from the local database.
     *
     * @param leagueName The name of the league.
     * @return A list of [TeamEntity] objects if they exist.
     */
    override suspend fun getAllTeamsByLeague(leagueName: String): List<TeamEntity> {
        return leagueDao.getTeamsByLeague(nameOfLeague = leagueName)
    }

    /**
     * Inserts a list of [TeamEntity] objects into the local database.
     *
     * @param list The list of [TeamEntity] objects to be inserted.
     */
    override suspend fun insertTeams(list: List<TeamEntity>) {
        leagueDao.insertAvailableTeams(teamList = list)
    }
}
