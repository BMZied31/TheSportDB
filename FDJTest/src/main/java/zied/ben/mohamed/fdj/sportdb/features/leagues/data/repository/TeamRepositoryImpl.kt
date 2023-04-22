package zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datahelpers.DataSourceDecisionMaker
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.TeamLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.TeamRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository
import javax.inject.Inject

/**
 * Implementation of [TeamRepository] which handles fetching team data from local and remote data sources.
 *
 * @property teamRemoteDataSource the remote data source to fetch data from
 * @property teamLocalDataSource the local data source to fetch data from
 * @property dataSourceDecisionMaker the decision maker which decides whether to fetch from remote or local data source
 */
class TeamRepositoryImpl @Inject constructor(
    private val teamRemoteDataSource: TeamRemoteDataSource,
    private val teamLocalDataSource: TeamLocalDataSource,
    private val dataSourceDecisionMaker: DataSourceDecisionMaker
) : TeamRepository {

    /**
     * Fetches the list of teams from either the remote or local data source based on [dataSourceDecisionMaker].
     *
     * @param leagueName the name of league which we want to retrieve its teams
     *
     * @return a [Flow] emitting the result of the operation,
     * which is a [FDJResult] containing either a list of [TeamModel] or an [Exception]
     */
    override suspend fun getTeamsByLeague(leagueName: String): Flow<FDJResult<List<TeamModel>>> {
        // check if data should be fetched from remote data source
        if (dataSourceDecisionMaker.shouldFetchTeamsFromRemote(leagueName)) {
            try {
                // fetch data from remote data source
                val remoteRawData = teamRemoteDataSource.getTeamsByLeague(leagueName).teams
                // map the response to database entities
                val listOfTeamsEntity = remoteRawData?.mapNotNull { teamResponse ->
                    teamResponse?.mapToDbEntity()
                }
                // update the local database
                listOfTeamsEntity?.let { list ->
                    teamLocalDataSource.insertTeams(list = list)
                }
            } catch (e: Exception) {
                Timber.e("error $e")
            }
        }

        // return data fetched from local data source
        return flow {
            emit(
                FDJResult.Success(
                    teamLocalDataSource.getAllTeamsByLeague(leagueName).map { leagueEntity ->
                        leagueEntity.mapToDomainModel()
                    }
                )
            )
        }
    }
}
