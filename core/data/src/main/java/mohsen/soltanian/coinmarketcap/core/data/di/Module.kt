package mohsen.soltanian.coinmarketcap.core.data.di


import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mohsen.soltanian.coinmarketcap.core.data.config.RemoteConfig
import mohsen.soltanian.coinmarketcap.core.data.extention.moshi
import mohsen.soltanian.coinmarketcap.core.data.network.interceptor.CacheInterceptor
import mohsen.soltanian.coinmarketcap.core.data.network.interceptor.CoinMarketCapsRequestInterceptor
import mohsen.soltanian.coinmarketcap.core.data.scopes.ServerService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val REMOTE_CONFIG = "remote_config"
private const val OKHTTP_CASH = "okhttp_cash"

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    @Singleton
    @Named(value = REMOTE_CONFIG)
    fun provideRemoteConfig(): RemoteConfig {
        return RemoteConfig()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(config: RemoteConfig): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (!config.isDev()) {
                HttpLoggingInterceptor.Level.NONE
            } else {
                HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    @Named(OKHTTP_CASH)
    fun provideOkHttpCache(@ApplicationContext context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir, "coin_market_cap_http-cache")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return  Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(value = REMOTE_CONFIG) config: RemoteConfig,
        @Named(OKHTTP_CASH) cache: Cache
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor {
                Timber.tag("REMOTE_API").e(it)
            }.apply {
                level = if (!config.isDev()) {
                    HttpLoggingInterceptor.Level.NONE
                } else {
                    HttpLoggingInterceptor.Level.BODY
                }
            })
            addNetworkInterceptor(CacheInterceptor())
            addInterceptor(CoinMarketCapsRequestInterceptor())
            cache(cache)
            connectTimeout(config.timeOut(), TimeUnit.SECONDS)
            readTimeout(config.timeOut(), TimeUnit.SECONDS)
            writeTimeout(config.timeOut(), TimeUnit.SECONDS)
            followSslRedirects(true)
            followRedirects(true)
            retryOnConnectionFailure(true)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return moshi
    }

    @Provides
    @Singleton
    @ServerService
    fun provideRetrofit(
        @Named(value = REMOTE_CONFIG) config: RemoteConfig,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(config.baseUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


}