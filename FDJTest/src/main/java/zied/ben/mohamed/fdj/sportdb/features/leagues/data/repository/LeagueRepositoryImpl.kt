package zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers.DataSourceDecisionMaker
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository
import javax.inject.Inject

/**
 * Implementation of [LeagueRepository] which handles fetching league data from local and remote data sources.
 *
 * @property leagueRemoteDataSource the remote data source to fetch data from
 * @property leagueLocalDataSource the local data source to fetch data from
 * @property dataSourceDecisionMaker the decision maker which decides whether to fetch from remote or local data source
 */
class LeagueRepositoryImpl @Inject constructor(
    private val leagueRemoteDataSource: LeagueRemoteDataSource,
    private val leagueLocalDataSource: LeagueLocalDataSource,
    private val dataSourceDecisionMaker: DataSourceDecisionMaker
) : LeagueRepository {

    /**
     * Fetches the list of leagues from either the remote or local data source based on [dataSourceDecisionMaker].
     *
     * @return a [Flow] emitting the result of the operation,
     * which is a [FDJResult] containing either a list of [LeagueModel] or an [Exception]
     */
    override suspend fun getLeagues(): Flow<FDJResult<List<LeagueModel>>> {
        // check if data should be fetched from remote data source
        if (dataSourceDecisionMaker.shouldFetchLeaguesFromRemote()) {
            try {
                // fetch data from remote data source
                val remoteRawData = leagueRemoteDataSource.getLeagues().leagues
                // map the response to database entities
                val leaguesEntity = remoteRawData?.mapNotNull { leagueResponse ->
                    leagueResponse?.mapToDbEntity()
                }
                // update the local database
                leaguesEntity?.let { list ->
                    leagueLocalDataSource.deleteAll()
                    leagueLocalDataSource.insertAllLeagues(list = list)
                }
            } catch (e: Exception) {
                Timber.e("error $e")
            }
        }

        // return data fetched from local data source
        return flow {
            emit(
                FDJResult.Success(
                    leagueLocalDataSource.getAllLeagues().map { leagueEntity ->
                        leagueEntity.mapToDomainModel()
                    }
                )
            )
        }
    }
}
