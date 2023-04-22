package zied.ben.mohamed.fdj.sportdb.features.leagues.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.TeamEntity

/**
 * The Room database for the app.
 */
@Database(entities = [LeagueEntity::class, TeamEntity::class], version = 1)
abstract class LeagueDatabase : RoomDatabase() {

    /**
     * Returns the DAO interface for managing league data.
     *
     * @return The DAO interface.
     */
    abstract fun leagueDao(): LeagueDao
}
