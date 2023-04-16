package zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository

import kotlinx.coroutines.flow.Flow
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel

interface LeagueRepository {

    suspend fun getLeagues(): Flow<FDJResult<List<LeagueModel>>>
}
