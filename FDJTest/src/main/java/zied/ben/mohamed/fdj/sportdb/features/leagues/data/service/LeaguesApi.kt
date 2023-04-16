package zied.ben.mohamed.fdj.sportdb.features.leagues.data.service

import retrofit2.http.GET
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto.LeagueWrapperResponse

interface LeaguesApi {

    @GET("all_leagues.php")
    suspend fun getLeagues(): LeagueWrapperResponse
}
