package zied.ben.mohamed.fdj.sportdb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zied.ben.mohamed.fdj.sportdb.BuildConfig
import zied.ben.mohamed.fdj.sportdb.features.leagues.data.service.LeaguesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Annotate only the method that provides the ApiService with Singleton.
     * No need to annotate all method with Singleton because when dagger access the provideApiService
     * it will directly return an instance as it's Singleton and it will not access the other @Provides methods,
     * therefore no need to add Singleton everywhere, thus we gain memory usage.
     *
     */
    @Singleton
    @Provides
    fun provideLeaguesApi(retrofit: Retrofit): LeaguesApi =
        retrofit.create(LeaguesApi::class.java)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("${BuildConfig.BASE_URL}${BuildConfig.APIKEY}/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val requestBuilder = request.newBuilder().build()
                // We can add specific authorization to headers here
                return@addInterceptor chain.proceed(requestBuilder)
            }
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
}
