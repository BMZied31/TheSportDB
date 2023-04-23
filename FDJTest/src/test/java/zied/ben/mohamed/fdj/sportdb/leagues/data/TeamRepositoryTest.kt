package zied.ben.mohamed.fdj.sportdb.leagues.data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers.DataSourceDecisionMaker
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.TeamLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.TeamRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamsWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.TeamEntity
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository.TeamRepositoryImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository

/**
 * Test class for [TeamRepository]
 */
class TeamRepositoryTest {

    // Mocks of the dependencies needed for testing the repository
    private val teamRemoteDataSource: TeamRemoteDataSource = mockk()
    private val teamLocalDataSource: TeamLocalDataSource = mockk()
    private val dataSourceDecisionMaker: DataSourceDecisionMaker = mockk()

    // Instance of the repository to be tested
    private val teamRepositoryImpl: TeamRepository = TeamRepositoryImpl(
        teamRemoteDataSource = teamRemoteDataSource,
        teamLocalDataSource = teamLocalDataSource,
        dataSourceDecisionMaker = dataSourceDecisionMaker
    )

    @Before
    fun before() {
        MockKAnnotations.init(this)
    }

    // Data for testing the repository
    private val localDbList = listOf(
        TeamEntity(teamId = "1", teamName = "PSG", leagueName = "Ligue 1"),
        TeamEntity(teamId = "2", teamName = "Lille", leagueName = "ligue 1")
    )

    private val remoteList = listOf(
        TeamResponse(idTeam = "1", strTeam = "PSG", strLeague = "ligue 1"),
        TeamResponse(idTeam = "2", strTeam = "Lille", strLeague = "ligue 1")
    )

    private val remoteNullResponse = TeamsWrapperResponse(teams = null)

    private val remoteResponseWithData = TeamsWrapperResponse(teams = remoteList)

    private val serverException = Exception("Internal Server Error")

    /**
     * Test scenario: shouldFetchFromRemote is true and remote throws exception
     * Expected behavior: Local db will not be updated
     */
    @Test
    fun `given shouldFetchFromRemote is true and remote throws exception, when get teams, then local db will not be updated`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchTeamsFromRemote(any()) } returns true
        coEvery { teamRemoteDataSource.getTeamsByLeague(any()) } throws serverException

        // Calling the function to be tested
        runBlocking {
            teamRepositoryImpl.getTeamsByLeague(anyString())
        }

        // Verify that [teamRemoteDataSource.getTeamsByLeague(any())] is called
        coVerify { teamRemoteDataSource.getTeamsByLeague(any()) }

        // Verify that [TeamLocalDataSource] was not called to update the database
        coVerify(inverse = true) { teamLocalDataSource.insertTeams(any()) }
    }

    /**
     * Test scenario: shouldFetchFromRemote is true and remote throws exception
     * Expected behavior: Data should be retrieved from local db
     */
    @Test
    fun `given shouldFetchFromRemote is true and remote throws exception, when get teams, then get data from local db`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchTeamsFromRemote(any()) } returns true
        coEvery { teamRemoteDataSource.getTeamsByLeague(any()) } throws serverException
        coEvery { teamLocalDataSource.getAllTeamsByLeague(any()) } returns localDbList

        var actualResult: List<TeamModel>? = null
        // Calling the function to be tested
        runBlocking {
            teamRepositoryImpl.getTeamsByLeague(anyString()).collect {
                actualResult = (it as FDJResult.Success).data
            }
        }

        // Verify that the result returned by the [teamRepositoryImpl.getTeamsByLeague] is fetched from local db
        Assert.assertEquals(localDbList.map { it.mapToDomainModel() }, actualResult)
    }

    /**
     * Test scenario: shouldFetchFromRemote is true and remote return null
     * Expected behavior: Local db will not be updated
     */
    @Test
    fun `given shouldFetchFromRemote is true and remote return null, when get teams, then local db will not be updated`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchTeamsFromRemote(any()) } returns true
        coEvery { teamRemoteDataSource.getTeamsByLeague(any()) } returns remoteNullResponse

        // Calling the function to be tested
        runBlocking {
            teamRepositoryImpl.getTeamsByLeague(anyString())
        }

        // Verify that [TeamLocalDataSource] was not called to update the database
        coVerify(inverse = true) { teamLocalDataSource.insertTeams(any()) }
    }

    /**
     * Test scenario: shouldFetchFromRemote is true and remote return correct data
     * Expected behavior: Local db will be updated
     */
    @Test
    fun `given shouldFetchFromRemote is true, when get teams, then local db will be updated`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchTeamsFromRemote(any()) } returns true
        coEvery { teamRemoteDataSource.getTeamsByLeague(any()) } returns remoteResponseWithData
        coEvery { teamLocalDataSource.insertTeams(any()) } returns Unit

        // Calling the function to be tested
        runBlocking {
            teamRepositoryImpl.getTeamsByLeague(anyString())
        }

        // Verify that [teamRemoteDataSource.getTeamsByLeague()] is called
        coVerify { teamRemoteDataSource.getTeamsByLeague(any()) }

        // Verify that [TeamLocalDataSource] was called to update the database
        coVerify(exactly = 1) { teamLocalDataSource.insertTeams(any()) }
    }

    /**
     * Test scenario: shouldFetchFromRemote is false
     * Expected behavior: Data should be retrieved from local db
     */
    @Test
    fun `given shouldFetchFromRemote is false, when get teams, then get data from local db without calling remote`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchTeamsFromRemote(any()) } returns false
        coEvery { teamLocalDataSource.getAllTeamsByLeague(any()) } returns localDbList

        var actualResult: List<TeamModel>? = null
        // Calling the function to be tested
        runBlocking {
            teamRepositoryImpl.getTeamsByLeague(anyString()).collect {
                actualResult = (it as FDJResult.Success).data
            }
        }

        // Verify that [teamRemoteDataSource.getTeamsByLeague] was not called
        coVerify(inverse = true) { teamRemoteDataSource.getTeamsByLeague(any()) }

        // Verify that [teamLocalDataSource.getAllTeamsByLeague] is called
        coVerify { teamLocalDataSource.getAllTeamsByLeague(any()) }

        // Verify that the result returned by the [teamRepositoryImpl.getAllTeamsByLeague] is fetched from local db
        Assert.assertEquals(localDbList.map { it.mapToDomainModel() }, actualResult)
    }
}
