package zied.ben.mohamed.fdj.sportdb.features.leagues.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.LeagueRepositoryImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.LeagueLocalDataSourceImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.LeagueRemoteDataSourceImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.LeagueRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class LeagueModule {

    @Binds
    abstract fun bindLeagueRepository(
        leagueRepositoryImpl: LeagueRepositoryImpl
    ): LeagueRepository

    @Binds
    abstract fun bindLeagueRemoteDataSource(
        leagueRemoteDataSourceImpl: LeagueRemoteDataSourceImpl
    ): LeagueRemoteDataSource

    @Binds
    abstract fun bindLeagueLocalDataSource(
        leagueLocalDataSourceImpl: LeagueLocalDataSourceImpl
    ): LeagueLocalDataSource
}
