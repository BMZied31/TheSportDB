package zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local

import zied.ben.mohamed.fdj.sportdb.features.leagues.data.entity.LeagueEntity

interface LeagueLocalDataSource {

    suspend fun getAllLeagues(): List<LeagueEntity>?

    suspend fun insertAllLeagues(list: List<LeagueEntity>)
}
