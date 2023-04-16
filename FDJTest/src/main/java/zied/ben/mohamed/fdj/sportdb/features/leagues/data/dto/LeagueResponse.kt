package zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto

import zied.ben.mohamed.fdj.sportdb.core.DatabaseEntityMapper
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity

/**
 * A data class representing a response from API that contains a list of [LeagueResponse] objects.
 *
 * @property leagues The list of leagues in the response.
 */
data class LeagueWrapperResponse(
    val leagues: List<LeagueResponse?>?
)

/**
 * A data class representing a single league from a [LeagueWrapperResponse].
 *
 * @property idLeague The ID of the league.
 * @property strLeague The name of the league.
 * @property strSport The sport of the league.
 * @property strLeagueAlternate The alternate name of the league.
 */
data class LeagueResponse(
    val idLeague: String?,
    val strLeague: String?,
    val strSport: String?,
    val strLeagueAlternate: String?
) : DatabaseEntityMapper<LeagueEntity> {

    /**
     * Maps this [LeagueResponse] object to a [LeagueEntity] db entity.
     *
     * @return The db entity.
     */
    override fun mapToDbEntity(): LeagueEntity =
        LeagueEntity(
            id = idLeague ?: "",
            leagueName = strLeague ?: "",
            sport = strSport ?: ""
        )
}
