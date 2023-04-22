package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.TeamEntity

/**
 * This interface defines the contract for accessing local data source for teams.
 */
interface TeamLocalDataSource {

    /**
     * Get all teams in the given league from local data source.
     *
     * @param leagueName The name of the league to get teams for.
     *
     * @return The list of [TeamEntity] objects if they exist in the data source, otherwise null.
     */
    suspend fun getAllTeamsByLeague(leagueName: String): List<TeamEntity>

    /**
     * Inserts the given list of [TeamEntity] objects into the local data source.
     *
     * @param list The list of [TeamEntity] objects to insert.
     */
    suspend fun insertTeams(list: List<TeamEntity>)
}
