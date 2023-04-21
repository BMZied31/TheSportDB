package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import zied.ben.mohamed.fdj.sportdb.core.BaseUseCase
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.di.DefaultDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository
import javax.inject.Inject

/**
 * A use case that retrieves a list of [LeagueModel] from the repository and sorts them by name.
 * The sorting is performed on a background thread using a [defaultDispatcher]
 *
 * @param leagueRepository the repository to retrieve the leagues from
 * @param defaultDispatcher the [CoroutineDispatcher] to use for sorting the list
 */
class GetLeaguesUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseUseCase<Flow<FDJResult<List<LeagueModel>>>> {

    /**
     * Invoke the use case
     *
     * @return A [Flow] of [FDJResult] of a sorted list of [LeagueModel] objects.
     */
    override suspend fun invoke(): Flow<FDJResult<List<LeagueModel>>> =
        // Apply a transformation on the flow returned from the repository
        leagueRepository.getLeagues().map { result ->
            when (result) {
                is FDJResult.Success -> {
                    // Sort the list on a background thread using the defaultDispatcher
                    val sortedList = withContext(defaultDispatcher) {
                        result.data.sortedBy { league -> league.name }
                    }
                    FDJResult.Success(sortedList)
                }
                is FDJResult.Failure -> result
            }
        }
}
