package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity

/**
 *This interface defines the contract for accessing local data source for leagues.
 */
interface LeagueLocalDataSource {

    /**
     * Retrieves all leagues from the local data source.
     *
     * @return A list of [LeagueEntity] objects.
     */
    suspend fun getAllLeagues(): List<LeagueEntity>

    /**
     * Inserts a list of all leagues into the local data source.
     *
     * @param list The list of [LeagueEntity] objects to insert.
     */
    suspend fun insertAllLeagues(list: List<LeagueEntity>)

    /**
     * Deletes all leagues from the local data source.
     */
    suspend fun deleteAll()
}
