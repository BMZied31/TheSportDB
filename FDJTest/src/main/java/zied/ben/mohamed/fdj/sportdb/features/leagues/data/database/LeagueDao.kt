package zied.ben.mohamed.fdj.sportdb.features.leagues.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity

@Dao
interface LeagueDao {

    @Query("SELECT * FROM leagueEntity")
    fun getAll(): List<LeagueEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(leaguesList: List<LeagueEntity>)

    @Query("DELETE FROM leagueEntity")
    fun deleteAll()
}
