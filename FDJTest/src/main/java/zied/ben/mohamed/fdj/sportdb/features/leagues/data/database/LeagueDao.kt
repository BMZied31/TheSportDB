package zied.ben.mohamed.fdj.sportdb.features.leagues.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.TeamEntity

/**
 * Data Access Object for the [LeagueEntity] and [TeamEntity] class.
 */
@Dao
interface LeagueDao {

    /**
     * Returns all leagues stored in the database.
     *
     * @return A list of [LeagueEntity] objects.
     */
    @Query("SELECT * FROM league")
    fun getAllLeagues(): List<LeagueEntity>

    /**
     * Inserts a list of all leagues into the database.
     *
     * @param leaguesList A list of [LeagueEntity] objects to be inserted.
     */
    @Insert
    fun insertAllLeagues(leaguesList: List<LeagueEntity>)

    /**
     * Deletes all leagues stored in the database.
     */
    @Query("DELETE FROM league")
    fun deleteAllLeagues()

    /**
     * Inserts a list of teams into the database.
     *
     * @param teamList A list of [TeamEntity] objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAvailableTeams(teamList: List<TeamEntity>)

    /**
     * Returns all teams that belong to the given league.
     *
     * @param nameOfLeague The name of the league.
     * @return A list of [TeamEntity] objects that belong to the league.
     */
    @Query("SELECT * FROM team WHERE (:nameOfLeague IN (leagueName, leagueName2, leagueName3, leagueName4, leagueName5, leagueName6, leagueName7))")
    fun getTeamsByLeague(nameOfLeague: String): List<TeamEntity>
}
