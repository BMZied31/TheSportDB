package zied.ben.mohamed.fdj.sportdb.features.leagues.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.TeamRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.TeamRemoteDataSourceImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository.TeamRepositoryImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class TeamModule {

    @Binds
    abstract fun bindTeamRepository(
        TeamRepositoryImpl: TeamRepositoryImpl
    ): TeamRepository

    @Binds
    abstract fun bindTeamRemoteDataSource(
        TeamRemoteDataSourceImpl: TeamRemoteDataSourceImpl
    ): TeamRemoteDataSource
}
