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

/**
 * Hilt module that provides dependencies related to data storage.
 */
@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    private const val preferencesName = "FDJTest_shared_preferences"

    /**
     * Provides the DAO for league data.
     * @param dataBase The league database to get the DAO from.
     * @return The DAO for league data.
     */
    @Singleton
    @Provides
    fun providesLeagueDao(dataBase: LeagueDatabase): LeagueDao =
        dataBase.leagueDao()

    /**
     * Provides the league database.
     * @param context The application context.
     * @return The league database.
     */
    @Singleton
    @Provides
    fun provideLeagueDb(@ApplicationContext context: Context): LeagueDatabase =
        Room.databaseBuilder(
            context,
            LeagueDatabase::class.java,
            "league-db"
        ).build()

    /**
     * Provides the encrypted shared preferences for the application.
     * @param context The application context.
     * @return The encrypted shared preferences.
     */
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
