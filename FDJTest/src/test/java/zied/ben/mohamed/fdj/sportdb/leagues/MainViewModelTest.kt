package zied.ben.mohamed.fdj.sportdb.leagues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase.GetLeaguesUseCase
import zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.MainViewModel

/**
 * Unit tests for the [MainViewModel] class.
 */
class MainViewModelTest {

    // Rule that swaps the background executor used by the Architecture Components with
    // a different one that runs each task synchronously.
    @get:Rule
    val taskExecutor = InstantTaskExecutorRule()

    // Mock instance of the [GetLeaguesUseCase] class.
    private val getLeaguesUseCaseMock: GetLeaguesUseCase = mockk()

    // Instance of the [MainViewModel] class under test.
    private val mainViewModel: MainViewModel =
        MainViewModel(getLeaguesUseCase = getLeaguesUseCaseMock)

    /**
     * Set up method that initializes the mock objects.
     */
    @Before
    fun before() {
        MockKAnnotations.init(this)
    }

    /**
     * Tests the case where the [GetLeaguesUseCase] returns a [FDJResult.Failure].
     * The test asserts that the error LiveData has the expected error message.
     */
    @Test
    fun `given result is Failure, when get leagues, then error liveData must have error message`() {
        val errorMessage = "Error Message"

        // Set up the mocked [GetLeaguesUseCase.invoke] method to return a failure result
        coEvery { getLeaguesUseCaseMock.invoke() } returns flow {
            emit(FDJResult.Failure(Throwable(errorMessage)))
        }

        // Call the function that is being tested.
        mainViewModel.getAllLeagues()

        // Verify that the [GetLeaguesUseCase.invoke] method was called once
        coVerify { getLeaguesUseCaseMock.invoke() }

        // Observe the LiveData and assert that the expected data is received.
        mainViewModel.error.observeForever { error ->
            Assert.assertEquals(errorMessage, error)
        }
    }

    /**
     * Tests the case where the [GetLeaguesUseCase] returns a [FDJResult.Success].
     * The test asserts that the leagues LiveData has the expected data.
     */
    @Test
    fun `given result is Success, when get leagues, then leagues liveData must have data`() {
        val leaguesList = listOf(LeagueModel())

        // Set up the mocked [GetLeaguesUseCase.invoke] method to return a success result
        coEvery { getLeaguesUseCaseMock.invoke() } returns flow {
            emit(FDJResult.Success(leaguesList))
        }

        // Call the function that is being tested.
        mainViewModel.getAllLeagues()

        // Verify that the [GetLeaguesUseCase.invoke] method was called once
        coVerify { getLeaguesUseCaseMock.invoke() }

        // Observe the LiveData and assert that the expected data is received.
        mainViewModel.leagues.observeForever { list ->
            Assert.assertEquals(leaguesList, list)
        }
    }

    /**
     * Tear down method that removes the observers from the LiveData objects.
     */
    @After
    fun after() {
        mainViewModel.error.removeObserver { }
        mainViewModel.leagues.removeObserver { }
    }
}
