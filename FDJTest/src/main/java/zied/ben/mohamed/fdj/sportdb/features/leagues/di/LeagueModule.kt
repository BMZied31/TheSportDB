package zied.ben.mohamed.fdj.sportdb.features.leagues.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSourceImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSourceImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository.LeagueRepositoryImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository

/**
 * A Hilt module that provides dependencies related to the League feature.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class LeagueModule {

    /**
     * Binds the implementation of the LeagueRepository interface to the LeagueRepositoryImpl class.
     */
    @Binds
    abstract fun bindLeagueRepository(
        leagueRepositoryImpl: LeagueRepositoryImpl
    ): LeagueRepository

    /**
     * Binds the implementation of the LeagueRemoteDataSource interface to the LeagueRemoteDataSourceImpl class.
     */
    @Binds
    abstract fun bindLeagueRemoteDataSource(
        leagueRemoteDataSourceImpl: LeagueRemoteDataSourceImpl
    ): LeagueRemoteDataSource

    /**
     * Binds the implementation of the LeagueLocalDataSource interface to the LeagueLocalDataSourceImpl class.
     */
    @Binds
    abstract fun bindLeagueLocalDataSource(
        leagueLocalDataSourceImpl: LeagueLocalDataSourceImpl
    ): LeagueLocalDataSource
}
