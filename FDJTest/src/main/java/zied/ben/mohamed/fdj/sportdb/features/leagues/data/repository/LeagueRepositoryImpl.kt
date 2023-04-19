package zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.di.IoDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers.DataSourceDecisionMaker
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leagueRemoteDataSource: LeagueRemoteDataSource,
    private val leagueLocalDataSource: LeagueLocalDataSource,
    private val dataSourceDecisionMaker: DataSourceDecisionMaker,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : LeagueRepository {

    override suspend fun getLeagues(): Flow<FDJResult<List<LeagueModel>>> =
        withContext(dispatcherIO) {
            if (dataSourceDecisionMaker.shouldFetchFromRemote()) {
                try {
                    val remoteRawData = leagueRemoteDataSource.getLeagues().leagues
                    val leaguesEntity = remoteRawData?.mapNotNull { leagueResponse ->
                        leagueResponse?.mapToDbEntity()
                    }
                    leaguesEntity?.let { list ->
                        leagueLocalDataSource.deleteAll()
                        leagueLocalDataSource.insertAllLeagues(list = list)
                    }
                } catch (e: Exception) {
                    Timber.e("error $e")
                }
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
        }
}
