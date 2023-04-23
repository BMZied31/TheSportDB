package zied.ben.mohamed.fdj.sportdb.leagues.domain

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase.GetLeaguesUseCase

/**
 * Unit tests for the [GetLeaguesUseCase] use case.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GetLeaguesUseCaseTest {

    // Mock instance of the [LeagueRepository] class.
    private val leagueRepository: LeagueRepository = mockk()

    // Test dispatcher to replace [Dispatchers.Default]
    private val testDispatcher = UnconfinedTestDispatcher()

    // Instance of the [GetLeaguesUseCase] class under test.
    private val getLeaguesUseCase: GetLeaguesUseCase =
        GetLeaguesUseCase(
            leagueRepository = leagueRepository,
            defaultDispatcher = testDispatcher
        )

    /**
     * Set up method that initializes the mock objects.
     */
    @Before
    fun before() {
        MockKAnnotations.init()
    }

    /**
     * Tests the case where the [LeagueRepository] returns a [FDJResult.Failure].
     * The use case must emit the error message.
     */
    @Test
    fun `given result is Failure, when call getLeagues, then must emit the error`() {
        val exceptedErrorMessage = "Error Message"

        // Set up the mocked [LeagueRepository.getLeagues] method to return a failure result
        coEvery { leagueRepository.getLeagues() } returns flow {
            emit(
                FDJResult.Failure(Throwable(exceptedErrorMessage))
            )
        }

        // Run the use case and collect the result
        var actualErrorMessage: String? = null
        runBlocking {
            getLeaguesUseCase.invoke().collect {
                actualErrorMessage = (it as FDJResult.Failure<*>).error?.message
            }
        }

        // Verify that the [LeagueRepository.getLeagues] method was called once
        coVerify { leagueRepository.getLeagues() }

        // Assert that the expected and actual error messages match
        Assert.assertEquals(exceptedErrorMessage, actualErrorMessage)
    }

    /**
     * Tests the case where the [LeagueRepository] returns a [FDJResult.Success].
     * The use case must emit a sorted list by name.
     */
    @Test
    fun `given result is Success, when call getLeagues, then must emit sorted list by name`() {
        val exceptedList = listOf(
            LeagueModel("1", "Zambia ligue 1", "football"),
            LeagueModel("2", "Austria ligue 1", "football")
        )

        // Set up the mocked [LeagueRepository.getLeagues] method to return a success result
        coEvery { leagueRepository.getLeagues() } returns flow {
            emit(
                FDJResult.Success(exceptedList)
            )
        }

        // Run the use case and collect the result
        var actualList = listOf<LeagueModel>()
        runBlocking {
            getLeaguesUseCase.invoke().collect {
                actualList = ((it as FDJResult.Success<*>).data) as List<LeagueModel>
            }
        }

        // Verify that the [LeagueRepository.getLeagues] method was called once
        coVerify { leagueRepository.getLeagues() }

        // Assert that the actual list is sorted by name and matches the sorted expected list
        Assert.assertEquals(exceptedList.sortedBy { it.name }, actualList)
    }
}
