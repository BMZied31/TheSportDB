package zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import zied.ben.mohamed.fdj.sportdb.core.DomainModelMapper
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel

/**
 * Entity class representing a league in the local database.
 *
 * @property idLeague The unique identifier of the league.
 * @property leagueName The name of the league.
 * @property sport The sport of the league.
 */
@Entity(tableName = "league")
data class LeagueEntity(
    @PrimaryKey val idLeague: String,
    val leagueName: String?,
    val sport: String?
) : DomainModelMapper<LeagueModel> {

    /**
     * Maps this [LeagueEntity] object to a [LeagueModel] domain model.
     *
     * @return The domain model.
     */
    override fun mapToDomainModel(): LeagueModel =
        LeagueModel(
            idLeague = idLeague,
            name = leagueName ?: "",
            sport = sport ?: ""
        )
}
