package zied.ben.mohamed.fdj.sportdb.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDao
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.database.LeagueDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    @Singleton
    @Provides
    fun providesLeagueDao(dataBase: LeagueDatabase): LeagueDao {
        return dataBase.leagueDao()
    }

    @Singleton
    @Provides
    fun provideLeagueDb(@ApplicationContext context: Context): LeagueDatabase =
        Room.databaseBuilder(
            context,
            LeagueDatabase::class.java,
            "league-db"
        ).build()
}
