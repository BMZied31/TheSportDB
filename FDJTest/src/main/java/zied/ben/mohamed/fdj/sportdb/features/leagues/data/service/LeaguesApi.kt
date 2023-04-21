package zied.ben.mohamed.fdj.sportdb.features.leagues.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.TeamsWrapperResponse

const val teams = "search_all_teams.php"
interface LeaguesApi {

    @GET("all_leagues.php")
    suspend fun getLeagues(): LeagueWrapperResponse

    @GET(teams)
    suspend fun getTeamsByLeague(@Query("l") leagueName: String): TeamsWrapperResponse
}
