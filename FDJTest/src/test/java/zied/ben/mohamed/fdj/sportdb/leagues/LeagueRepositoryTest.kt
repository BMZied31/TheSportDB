package zied.ben.mohamed.fdj.sportdb.leagues

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers.DataSourceDecisionMaker
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository.LeagueRepositoryImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository

/**
 * Test class for [LeagueRepository]
 */
class LeagueRepositoryTest {

    // Mocks of the dependencies needed for testing the repository
    private val leagueRemoteDataSource: LeagueRemoteDataSource = mockk()
    private val leagueLocalDataSource: LeagueLocalDataSource = mockk()
    private val dataSourceDecisionMaker: DataSourceDecisionMaker = mockk()

    // Instance of the repository to be tested
    private val leagueRepositoryImpl: LeagueRepository = LeagueRepositoryImpl(
        leagueRemoteDataSource = leagueRemoteDataSource,
        leagueLocalDataSource = leagueLocalDataSource,
        dataSourceDecisionMaker = dataSourceDecisionMaker
    )

    @Before
    fun before() {
        MockKAnnotations.init(this)
    }

    // Data for testing the repository
    private val localDbList = listOf(
        LeagueEntity("1", "ligue1", "foot"),
        LeagueEntity("2", "ligue2", "foot")
    )

    private val remoteList = listOf(
        LeagueResponse("1", "ligue1", "foot", ""),
        LeagueResponse("2", "ligue2", "foot", "")
    )

    private val remoteNullResponse = LeagueWrapperResponse(leagues = null)

    private val remoteResponseWithData = LeagueWrapperResponse(leagues = remoteList)

    private val serverException = Exception("Internal Server Error")

    /**
     * Test scenario: shouldFetchFromRemote is true and remote throws exception
     * Expected behavior: Local db will not be updated
     */
    @Test
    fun `given shouldFetchFromRemote is true and remote throws exception, when get leagues, then local db will not be updated`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchLeaguesFromRemote() } returns true
        coEvery { leagueRemoteDataSource.getLeagues() } throws serverException

        // Calling the function to be tested
        runBlocking {
            leagueRepositoryImpl.getLeagues()
        }

        // Verify that [leagueRemoteDataSource.getLeagues()] is called
        coVerify { leagueRemoteDataSource.getLeagues() }

        // Verify that [LeagueLocalDataSource] was not called to update the database
        coVerify(inverse = true) { leagueLocalDataSource.deleteAll() }
        coVerify(inverse = true) { leagueLocalDataSource.insertAllLeagues(any()) }
    }

    /**
     * Test scenario: shouldFetchFromRemote is true and remote throws exception
     * Expected behavior: Data should be retrieved from local db
     */
    @Test
    fun `given shouldFetchFromRemote is true and remote throws exception, when get leagues, then get data from local db`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchLeaguesFromRemote() } returns true
        coEvery { leagueRemoteDataSource.getLeagues() } throws serverException
        coEvery { leagueLocalDataSource.getAllLeagues() } returns localDbList

        var actualResult: List<LeagueModel>? = null
        // Calling the function to be tested
        runBlocking {
            leagueRepositoryImpl.getLeagues().collect {
                actualResult = (it as FDJResult.Success).data
            }
        }

        // Verify that the result returned by the [leagueRepositoryImpl.getLeagues] is fetched from local db
        Assert.assertEquals(localDbList.map { it.mapToDomainModel() }, actualResult)
    }

    /**
     * Test scenario: shouldFetchFromRemote is true and remote return null
     * Expected behavior: Local db will not be updated
     */
    @Test
    fun `given shouldFetchFromRemote is true and remote return null, when get leagues, then local db will not be updated`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchLeaguesFromRemote() } returns true
        coEvery { leagueRemoteDataSource.getLeagues() } returns remoteNullResponse

        // Calling the function to be tested
        runBlocking {
            leagueRepositoryImpl.getLeagues()
        }

        // Verify that [LeagueLocalDataSource] was not called to update the database
        coVerify(inverse = true) { leagueLocalDataSource.deleteAll() }
        coVerify(inverse = true) { leagueLocalDataSource.insertAllLeagues(any()) }
    }

    /**
     * Test scenario: shouldFetchFromRemote is true and remote return correct data
     * Expected behavior: Local db will be updated
     */
    @Test
    fun `given shouldFetchFromRemote is true, when get leagues, then local db will be updated`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchLeaguesFromRemote() } returns true
        coEvery { leagueRemoteDataSource.getLeagues() } returns remoteResponseWithData
        coEvery { leagueLocalDataSource.deleteAll() } returns Unit
        coEvery { leagueLocalDataSource.insertAllLeagues(any()) } returns Unit

        // Calling the function to be tested
        runBlocking {
            leagueRepositoryImpl.getLeagues()
        }

        // Verify that [leagueRemoteDataSource.getLeagues()] is called
        coVerify { leagueRemoteDataSource.getLeagues() }

        // Verify that [LeagueLocalDataSource] was called to update the database
        coVerify(exactly = 1) { leagueLocalDataSource.deleteAll() }
        coVerify(exactly = 1) { leagueLocalDataSource.insertAllLeagues(any()) }
    }

    /**
     * Test scenario: shouldFetchFromRemote is false
     * Expected behavior: Data should be retrieved from local db
     */
    @Test
    fun `given shouldFetchFromRemote is false, when get leagues, then get data from local db without calling remote`() {
        // Mocking the behavior of the dependencies
        coEvery { dataSourceDecisionMaker.shouldFetchLeaguesFromRemote() } returns false
        coEvery { leagueLocalDataSource.getAllLeagues() } returns localDbList

        var actualResult: List<LeagueModel>? = null
        // Calling the function to be tested
        runBlocking {
            leagueRepositoryImpl.getLeagues().collect {
                actualResult = (it as FDJResult.Success).data
            }
        }

        // Verify that [leagueRemoteDataSource.getLeagues()] was not called
        coVerify(inverse = true) { leagueRemoteDataSource.getLeagues() }

        // Verify that [leagueLocalDataSource.getAllLeagues()] is called
        coVerify { leagueLocalDataSource.getAllLeagues() }

        // Verify that the result returned by the [leagueRepositoryImpl.getLeagues] is fetched from local db
        Assert.assertEquals(localDbList.map { it.mapToDomainModel() }, actualResult)
    }
}
