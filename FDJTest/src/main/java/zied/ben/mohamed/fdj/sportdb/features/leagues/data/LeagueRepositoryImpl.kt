package zied.ben.mohamed.fdj.sportdb.features.leagues.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.service.LeaguesApi
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leaguesApi: LeaguesApi
) : LeagueRepository {

    override suspend fun getLeagues(): Flow<FDJResult<List<LeagueModel>>> = flow {
        emit(
            try {
                FDJResult.Success(
                    leaguesApi.getLeagues().leagues?.map { leagueResponse ->
                        leagueResponse?.mapToDomainModel() ?: LeagueModel()
                    } ?: listOf()
                )
            } catch (e: Exception) {
                FDJResult.Failure(e)
            }
        )
    }
}
