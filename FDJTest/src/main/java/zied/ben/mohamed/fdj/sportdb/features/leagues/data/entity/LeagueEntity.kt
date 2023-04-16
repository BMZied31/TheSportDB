package zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import zied.ben.mohamed.fdj.sportdb.core.DomainModelMapper
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel

@Entity
data class LeagueEntity(
    @PrimaryKey val id: String,
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
            idLeague = id,
            name = leagueName ?: "",
            sport = sport ?: ""
        )
}
