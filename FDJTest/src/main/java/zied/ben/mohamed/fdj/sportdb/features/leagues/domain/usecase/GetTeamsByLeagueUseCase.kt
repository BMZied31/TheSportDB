package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import zied.ben.mohamed.fdj.sportdb.core.BaseUseCaseWithRequest
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.di.DefaultDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository
import javax.inject.Inject

/**
 * A use case that retrieves a list of [TeamModel] from the repository and sorts them by name
 * in a descending order while filtering 1 team out of 2.
 * The sorting is performed on a background thread using a [defaultDispatcher]
 *
 * @param teamRepository the repository to retrieve the teams from
 * @param defaultDispatcher the [CoroutineDispatcher] to use for sorting and filtering the list
 */
class GetTeamsByLeagueUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseUseCaseWithRequest<String, Flow<FDJResult<List<TeamModel>>>> {

    /**
     * Invoke the use case
     *
     * @return A [Flow] of [FDJResult] of a sorted list of [TeamModel] containing 1 team out of 2
     * returned from [TeamRepository].
     */
    override suspend fun invoke(request: String): Flow<FDJResult<List<TeamModel>>> =
        // Apply a transformation on the flow returned from the repository
        teamRepository.getTeamsByLeague(leagueName = request).map { result ->
            when (result) {
                is FDJResult.Success -> {
                    // Sort and return 1 team out of 2 from the list
                    val sortedList = withContext(defaultDispatcher) {
                        result.data.sortedByDescending { team -> team.teamName }
                            .filterIndexed { index, _ -> index % 2 == 0 }
                    }
                    FDJResult.Success(sortedList)
                }
                is FDJResult.Failure -> result
            }
        }
}
