package zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.di.IoDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers.DataSourceDecisionMaker
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.TeamRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamRemoteDataSource: TeamRemoteDataSource,
    private val dataSourceDecisionMaker: DataSourceDecisionMaker
) : TeamRepository {

    override suspend fun getTeamsByLeague(leagueName: String): Flow<FDJResult<List<TeamModel>>> =
        flow {
            try {
                emit(
                    FDJResult.Success(
                        teamRemoteDataSource.getTeamsByLeague(leagueName).teams?.mapNotNull { teamsResponse ->
                            teamsResponse?.mapToDbEntity()
                        } ?: listOf()
                    )
                )
            } catch (e: Exception) {
                emit(FDJResult.Failure(Throwable(e)))
            }
        }
}
