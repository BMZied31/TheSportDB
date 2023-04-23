package zied.ben.mohamed.fdj.sportdb.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDao
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDatabase
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.TeamEntity

/**
 * Tests for [LeagueDao].
 */
@RunWith(AndroidJUnit4::class)
class DaoTest {

    // DAO and database instances
    private lateinit var leagueDao: LeagueDao
    private lateinit var leagueDatabase: LeagueDatabase

    // Sample data for testing
    private val leaguesList = listOf(
        LeagueEntity(idLeague = "1", leagueName = "ligue 1", sport = "football"),
        LeagueEntity(idLeague = "2", leagueName = "ligue 2", sport = "football")
    )

    private val ligue1Teams = listOf(
        TeamEntity(teamId = "1", teamName = "PSG", leagueName = "ligue 1", leagueName2 = "UEFA CL"),
        TeamEntity(teamId = "2", teamName = "OM", leagueName = "ligue 1", leagueName2 = "UEFA CL"),
        TeamEntity(teamId = "3", teamName = "Rennes", leagueName = "ligue 1", leagueName2 = "UEFA Europa")
    )

    private val uefaCLTeams = listOf(
        TeamEntity(teamId = "4", teamName = "Man city", leagueName = "English PL", leagueName2 = "UEFA CL"),
        TeamEntity(teamId = "5", teamName = "AC Milan", leagueName = "Serie A", leagueName2 = "UEFA CL"),
        TeamEntity(teamId = "1", teamName = "PSG", leagueName = "ligue 1", leagueName2 = "UEFA CL"),
        TeamEntity(teamId = "2", teamName = "OM", leagueName = "ligue 1", leagueName2 = "UEFA CL")
    )

    /**
     * Creates an in-memory database and initializes DAO instance before each test.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        leagueDatabase = Room.inMemoryDatabaseBuilder(
            context,
            LeagueDatabase::class.java
        ).build()
        leagueDao = leagueDatabase.leagueDao()
    }

    /**
     * Closes the database after each test.
     */
    @After
    fun closeDb() {
        leagueDatabase.close()
    }

    /**
     * Tests inserting leagues and then reading all leagues from the database.
     */
    @Test
    fun writeLeaguesAndReadLeagues() {
        leagueDao.insertAllLeagues(leaguesList)
        val allLeaguesInDb = leagueDao.getAllLeagues()
        Assert.assertEquals(leaguesList, allLeaguesInDb)
    }

    /**
     * Tests inserting teams and then reading teams by league from the database.
     */
    @Test
    fun writeTeamsWithCommonLeaguesAndCheckReadForEachLeague() {
        leagueDao.insertAvailableTeams(ligue1Teams)
        leagueDao.insertAvailableTeams(uefaCLTeams)

        // Retrieve teams by league name
        val ligue1TeamsInDb = leagueDao.getTeamsByLeague(nameOfLeague = "ligue 1")
        val uefaCLTeamsInDb = leagueDao.getTeamsByLeague(nameOfLeague = "UEFA CL")

        // Check if the retrieved teams match with the original data, sorted by team name
        Assert.assertEquals(ligue1Teams.sortedBy { it.teamName }, ligue1TeamsInDb)
        Assert.assertEquals(uefaCLTeams.sortedBy { it.teamName }, uefaCLTeamsInDb)
    }
}
