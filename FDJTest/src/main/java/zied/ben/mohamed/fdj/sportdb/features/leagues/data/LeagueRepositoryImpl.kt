package zied.ben.mohamed.fdj.sportdb.features.leagues.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository
import zied.ben.mohamed.fdj.sportdb.utils.CheckConnection
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leagueRemoteDataSource: LeagueRemoteDataSource,
    private val leagueLocalDataSource: LeagueLocalDataSource,
    private val checkConnection: CheckConnection
) : LeagueRepository {

    override suspend fun getLeagues(): Flow<FDJResult<List<LeagueModel>>> =
        try {
            if (checkConnection.isConnected()) {
                val remoteRawData = leagueRemoteDataSource.getLeagues().leagues
                val leaguesEntity = remoteRawData?.mapNotNull { leagueResponse ->
                    leagueResponse?.mapToDbEntity()
                }
                leaguesEntity?.let { list -> leagueLocalDataSource.insertAllLeagues(list = list) }
            }

            flow {
                emit(
                    FDJResult.Success(
                        leagueLocalDataSource.getAllLeagues()?.map { leagueEntity ->
                            leagueEntity.mapToDomainModel()
                        } ?: listOf()
                    )
                )
            }
        } catch (e: Exception) {
            flow {
                emit(FDJResult.Failure(e))
            }
        }
}
