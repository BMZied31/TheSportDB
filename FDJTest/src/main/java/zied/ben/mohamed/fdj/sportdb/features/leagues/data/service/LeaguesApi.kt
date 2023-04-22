package zied.ben.mohamed.fdj.sportdb.features.leagues.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamsWrapperResponse

/**
 * This interface defines the API endpoints for retrieving data related to leagues and teams.
 */
interface LeaguesApi {

    /**
     * This function is used to get all leagues from the API.
     *
     * @return the response containing the list of all leagues.
     */
    @GET("all_leagues.php")
    suspend fun getLeagues(): LeagueWrapperResponse

    /**
     * This function is used to get all teams from the API based on the league name.
     *
     * @param leagueName the name of the league for which teams are to be retrieved.
     * @return the response containing the list of teams for the specified league.
     */
    @GET("search_all_teams.php")
    suspend fun getTeamsByLeague(@Query("l") leagueName: String): TeamsWrapperResponse
}
