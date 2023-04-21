package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse

/**
 * This interface defines the contract for retrieving the list of leagues from api.
 */
interface LeagueRemoteDataSource {

    /**
     * Fetches a list of leagues.
     *
     * @return [LeagueWrapperResponse] object containing the raw leagues data.
     */
    suspend fun getLeagues(): LeagueWrapperResponse
}
