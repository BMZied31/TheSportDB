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
import org.mockito.ArgumentMatchers.anyString
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase.GetTeamsByLeagueUseCase

/**
 * Unit tests for the [GetTeamsByLeagueUseCase] use case.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GetTeamsUseCaseTest {

    // Mock instance of the [TeamRepository] class.
    private val teamRepository: TeamRepository = mockk()

    // Test dispatcher to replace [Dispatchers.Default]
    private val testDispatcher = UnconfinedTestDispatcher()

    // Instance of the [GetLeaguesUseCase] class under test.
    private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase =
        GetTeamsByLeagueUseCase(
            teamRepository = teamRepository,
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
     * Tests the case where the [TeamRepository] returns a [FDJResult.Failure].
     * The use case must emit the error message.
     */
    @Test
    fun `given result is Failure, when call getTeamsByLeague, then must emit the error`() {
        val exceptedErrorMessage = "Error Message"

        // Set up the mocked [TeamRepository.getTeamsByLeague] method to return a failure result
        coEvery { teamRepository.getTeamsByLeague(any()) } returns flow {
            emit(
                FDJResult.Failure(Throwable(exceptedErrorMessage))
            )
        }

        // Run the use case and collect the result
        var actualErrorMessage: String? = null
        runBlocking {
            getTeamsByLeagueUseCase.invoke(anyString()).collect {
                actualErrorMessage = (it as FDJResult.Failure<*>).error?.message
            }
        }

        // Verify that the [TeamRepository.getTeamsByLeague] method was called once
        coVerify { teamRepository.getTeamsByLeague(any()) }

        // Assert that the expected and actual error messages match
        Assert.assertEquals(exceptedErrorMessage, actualErrorMessage)
    }

    /**
     * Tests the case where the [TeamRepository] returns a [FDJResult.Success].
     * The use case must emit a sorted by name (descending) and filtered (only 1 team out of 2) list
     */
    @Test
    fun `given result is Success, when call getTeamsByLeague, then must emit sorted and filtered list`() {
        val listReturnedByRepository = listOf(
            TeamModel(id = "1", teamName = "Angers", badge = null),
            TeamModel(id = "2", teamName = "Brest", badge = null),
            TeamModel(id = "3", teamName = "Nice", badge = null),
            TeamModel(id = "4", teamName = "PSG", badge = null)
        )

        // expected list that's sorted in descending order by name and is filtered to have 1 team out of 2
        val exceptedListReturnedByUseCase = listOf(
            TeamModel(id = "4", teamName = "PSG", badge = null),
            TeamModel(id = "2", teamName = "Brest", badge = null)
        )

        // Set up the mocked [TeamRepository.getTeamsByLeague] method to return a success result
        coEvery { teamRepository.getTeamsByLeague(any()) } returns flow {
            emit(
                FDJResult.Success(listReturnedByRepository)
            )
        }

        // Run the use case and collect the result
        var actualList = listOf<TeamModel>()
        runBlocking {
            getTeamsByLeagueUseCase.invoke(anyString()).collect {
                actualList = ((it as FDJResult.Success<*>).data) as List<TeamModel>
            }
        }

        // Verify that the [TeamRepository.getTeamsByLeague] method was called once
        coVerify { teamRepository.getTeamsByLeague(any()) }

        // Assert that the actual list is sorted in descending order by name and is filtered to have 1 team out of 2
        Assert.assertEquals(exceptedListReturnedByUseCase, actualList)
    }
}
