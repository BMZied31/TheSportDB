package zied.ben.mohamed.fdj.sportdb.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
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

    private const val preferencesName = "FDJTest_shared_preferences"

    @Singleton
    @Provides
    fun providesLeagueDao(dataBase: LeagueDatabase): LeagueDao =
        dataBase.leagueDao()

    @Singleton
    @Provides
    fun provideLeagueDb(@ApplicationContext context: Context): LeagueDatabase =
        Room.databaseBuilder(
            context,
            LeagueDatabase::class.java,
            "league-db"
        ).build()

    @Singleton
    @Provides
    fun provideEncryptedSharedPref(@ApplicationContext context: Context): SharedPreferences {
        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            preferencesName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
