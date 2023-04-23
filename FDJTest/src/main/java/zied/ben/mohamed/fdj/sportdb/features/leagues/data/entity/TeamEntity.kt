package zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import zied.ben.mohamed.fdj.sportdb.core.DomainModelMapper
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel

/**
 * Entity class representing a team in the local database.
 *
 * @property teamId The unique ID of the team.
 * @property teamName The name of the team.
 * @property teamBadge The URL of the team's badge image.
 * @property leagueName The name of the team's first league.
 * @property leagueName2 The name of the team's second league.
 * @property leagueName3 The name of the team's third league.
 * @property leagueName4 The name of the team's fourth league.
 * @property leagueName5 The name of the team's fifth league.
 * @property leagueName6 The name of the team's sixth league.
 * @property leagueName7 The name of the team's seventh league.
 */
@Entity(tableName = "team")
data class TeamEntity(
    @PrimaryKey val teamId: String,
    val teamName: String = "",
    val teamBadge: String? = null,
    val leagueName: String?,
    val leagueName2: String? = null,
    val leagueName3: String? = null,
    val leagueName4: String? = null,
    val leagueName5: String? = null,
    val leagueName6: String? = null,
    val leagueName7: String? = null
) : DomainModelMapper<TeamModel> {

    /**
     * Maps this [TeamEntity] object to a [TeamModel] domain model.
     *
     * @return The domain model.
     */
    override fun mapToDomainModel(): TeamModel =
        TeamModel(
            id = teamId,
            teamName = teamName,
            badge = teamBadge
        )
}
