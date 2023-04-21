package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import zied.ben.mohamed.fdj.sportdb.core.BaseUseCaseWithRequest
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.di.DefaultDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository
import javax.inject.Inject

class GetTeamsByLeagueUseCase @Inject constructor(
    private val teamsRepository: TeamRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseUseCaseWithRequest<String, Flow<FDJResult<List<TeamModel>>>> {

    override suspend fun invoke(request: String): Flow<FDJResult<List<TeamModel>>> =
        teamsRepository.getTeamsByLeague(request)
}
