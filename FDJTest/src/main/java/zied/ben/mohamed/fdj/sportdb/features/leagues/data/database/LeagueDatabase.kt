package zied.ben.mohamed.fdj.sportdb.features.leagues.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity

@Database(entities = [LeagueEntity::class], version = 1)
abstract class LeagueDatabase : RoomDatabase() {

    abstract fun leagueDao(): LeagueDao
}
