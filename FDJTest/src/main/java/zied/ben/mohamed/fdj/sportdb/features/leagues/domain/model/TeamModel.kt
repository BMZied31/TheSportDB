package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model

/**
 * A data class representing a domain model of teams.
 *
 * @property id The ID of the league.
 * @property teamName The name of the league.
 * @property badge The url of the team badge.
 */
data class TeamModel(
    val id: String,
    val teamName: String = "",
    val badge: String?
) {

    /**
     * Creates a new instance of the [TeamModel] class with empty strings for all parameters.
     */
    constructor() : this(id = "", teamName = "", badge = "")
}
