package zied.ben.mohamed.fdj.sportdb.features.leagues.data.dto

import zied.ben.mohamed.fdj.sportdb.core.DatabaseEntityMapper
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.TeamEntity

/**
 * A data class representing a response from API that contains a list of [TeamResponse] objects.
 *
 * @property teams The list of leagues in the response.
 */
data class TeamsWrapperResponse(
    val teams: List<TeamResponse?>?
)

/**
 * A data class representing a single league from a [TeamsWrapperResponse].
 *
 * @property idTeam The ID of the team.
 * @property strTeam The name of the team.
 * @property strAlternate The alternative name of the team.
 * @property intFormedYear The year the team was formed.
 * @property strSport The sport played by the team.
 * @property strLeague The name of the primary league the team plays in.
 * @property idLeague The ID of the primary league the team plays in.
 * @property strLeague2 The name of the secondary league the team plays in.
 * @property idLeague2 The ID of the secondary league the team plays in.
 * @property strLeague3 The name of the tertiary league the team plays in.
 * @property idLeague3 The ID of the tertiary league the team plays in.
 * @property strLeague4 The name of the quaternary league the team plays in.
 * @property idLeague4 The ID of the quaternary league the team plays in.
 * @property strLeague5 The name of the quinary league the team plays in.
 * @property idLeague5 The ID of the quinary league the team plays in.
 * @property strLeague6 The name of the senary league the team plays in.
 * @property idLeague6 The ID of the senary league the team plays in.
 * @property strLeague7 The name of the septenary league the team plays in.
 * @property idLeague7 The ID of the septenary league the team plays in.
 * @property strStadium The name of the team's stadium.
 * @property strStadiumThumb The URL of an image of the team's stadium.
 * @property strStadiumDescription A description of the team's stadium.
 * @property strStadiumLocation The location of the team's stadium.
 * @property strWebsite The URL of the team's official website.
 * @property strFacebook The URL of the team's Facebook page.
 * @property strTwitter The URL of the team's Twitter page.
 * @property strInstagram The URL of the team's Instagram page.
 * @property strYoutube The URL of the team's YouTube channel.
 * @property strDescriptionEN A description of the team in English.
 * @property strCountry The country where the team is located.
 * @property strTeamBadge The URL of an image of the team's badge.
 * @property strTeamJersey The URL of an image of the team's jersey.
 */
data class TeamResponse(
    val idTeam: String?,
    val strTeam: String?,
    val strAlternate: String? = null,
    val intFormedYear: String? = null,
    val strSport: String? = null,
    val strLeague: String? = null,
    val idLeague: String? = null,
    val strLeague2: String? = null,
    val idLeague2: String? = null,
    val strLeague3: String? = null,
    val idLeague3: String? = null,
    val strLeague4: String? = null,
    val idLeague4: String? = null,
    val strLeague5: String? = null,
    val idLeague5: String? = null,
    val strLeague6: String? = null,
    val idLeague6: String? = null,
    val strLeague7: String? = null,
    val idLeague7: String? = null,
    val strStadium: String? = null,
    val strStadiumThumb: String? = null,
    val strStadiumDescription: String? = null,
    val strStadiumLocation: String? = null,
    val strWebsite: String? = null,
    val strFacebook: String? = null,
    val strTwitter: String? = null,
    val strInstagram: String? = null,
    val strYoutube: String? = null,
    val strDescriptionEN: String? = null,
    val strCountry: String? = null,
    val strTeamBadge: String? = null,
    val strTeamJersey: String? = null
) : DatabaseEntityMapper<TeamEntity> {

    /**
     * Maps this [TeamResponse] object to a [TeamEntity] db entity.
     *
     * @return The db entity.
     */
    override fun mapToDbEntity(): TeamEntity {
        return TeamEntity(
            teamId = idTeam ?: "",
            teamName = strTeam ?: "",
            teamBadge = strTeamBadge,
            leagueName = this.strLeague,
            leagueName2 = this.strLeague2,
            leagueName3 = this.strLeague3,
            leagueName4 = this.strLeague4,
            leagueName5 = this.strLeague5,
            leagueName6 = this.strLeague6,
            leagueName7 = this.strLeague7
        )
    }
}
