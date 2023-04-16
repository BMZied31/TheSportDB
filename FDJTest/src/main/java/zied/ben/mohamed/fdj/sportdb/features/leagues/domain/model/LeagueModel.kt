package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model

/**
 * A data class representing a domain model of sports league.
 *
 * @property idLeague The ID of the league.
 * @property name The name of the league.
 * @property sport The sport of the league.
 */
data class LeagueModel(
    val idLeague: String?,
    val name: String,
    val sport: String
) {

    /**
     * Creates a new instance of the `LeagueModel` class with null values for `idLeague`,
     * and empty strings for `name` and `sport`.
     */
    constructor() : this(idLeague = null, name = "", sport = "")
}
