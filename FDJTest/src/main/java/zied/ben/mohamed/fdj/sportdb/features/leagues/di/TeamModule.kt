package zied.ben.mohamed.fdj.sportdb.features.leagues.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.TeamLocalDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.local.TeamLocalDataSourceImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.TeamRemoteDataSource
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.datasource.remote.TeamRemoteDataSourceImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.repository.TeamRepositoryImpl
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.respository.TeamRepository

/**
 * A Hilt module that provides dependencies related to the Team feature.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class TeamModule {

    /**
     * Binds the implementation of the TeamRepository interface to the TeamRepositoryImpl class.
     */
    @Binds
    abstract fun bindTeamRepository(
        TeamRepositoryImpl: TeamRepositoryImpl
    ): TeamRepository

    /**
     * Binds the implementation of the TeamRemoteDataSource interface to the TeamRemoteDataSourceImpl class.
     */
    @Binds
    abstract fun bindTeamRemoteDataSource(
        TeamRemoteDataSourceImpl: TeamRemoteDataSourceImpl
    ): TeamRemoteDataSource

    /**
     * Binds the implementation of the TeamLocalDataSource interface to the TeamLocalDataSourceImpl class.
     */
    @Binds
    abstract fun bindTeamLocalDataSource(
        teamLocalDataSourceImpl: TeamLocalDataSourceImpl
    ): TeamLocalDataSource
}
