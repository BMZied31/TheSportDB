package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import zied.ben.mohamed.fdj.sportdb.core.BaseUseCase
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.di.IoDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository
import javax.inject.Inject

class GetLeaguesUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : BaseUseCase<Flow<FDJResult<List<LeagueModel>>>> {

    override suspend fun invoke(): Flow<FDJResult<List<LeagueModel>>> =
        withContext(dispatcherIO) {
            flow {
                leagueRepository.getLeagues().collect { result ->
                    when (result) {
                        is FDJResult.Success -> {
                            emit(FDJResult.Success(result.data.sortedBy { league -> league.name }))
                        }
                        is FDJResult.Failure -> emit(result)
                    }
                }
            }
        }
}
