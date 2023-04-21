package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model

/**
 * A data class representing a domain model of sports league.
 *
 * @property idLeague The ID of the league.
 * @property name The name of the league.
 * @property sport The sport of the league.
 */
data class TeamModel(
    val id: String = "",
    val teamName: String = "",
    val teamAlternateName: String = "",
    val leagueName1: String = "",
    val idLeague1: String = "",
    val leagueName2: String? = null,
    val idLeague2: String? = null,
    val leagueName3: String? = null,
    val idLeague3: String? = null,
    val leagueName4: String? = null,
    val idLeague4: String? = null,
    val leagueName5: String? = null,
    val idLeague5: String? = null,
    val leagueName6: String? = null,
    val idLeague6: String? = null,
    val leagueName7: String? = null,
    val idLeague7: String? = null,
    val teamBadge: String? = null
)
