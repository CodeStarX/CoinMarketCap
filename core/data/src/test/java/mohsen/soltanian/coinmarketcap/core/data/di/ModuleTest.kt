package mohsen.soltanian.coinmarketcap.core.data.di

import android.content.Context
import com.squareup.moshi.Moshi
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import mohsen.soltanian.coinmarketcap.core.data.config.RemoteConfig
import mohsen.soltanian.coinmarketcap.core.data.core.DataUnitTest
import mohsen.soltanian.coinmarketcap.core.data.enviroment.CoreEnvironment
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.amshove.kluent.*
import org.junit.Test
import retrofit2.Retrofit
import java.io.File


class ModuleTest : DataUnitTest() {


    @Test
    fun `verify provide Remote Config`() {
        val remoteConfig = mockk<RemoteConfig> {
            every { timeOut() } returns 30L
            every { isDev() } returns false
            every { baseUrl() } returns "https://pro-api.coinmarketcap.com/"
            every { environment() } returns CoreEnvironment.PUBLIC
        }
        val module = mockk<Module>()
        every { module.provideRemoteConfig() } returns remoteConfig
        val config = module.provideRemoteConfig()
        config `should be instance of` RemoteConfig::class.java
        config.timeOut() `should equal` 30L
        config.isDev() `should equal to` false
        config.baseUrl() `should equal to` "https://pro-api.coinmarketcap.com/"
        config.environment() `should equal` CoreEnvironment.PUBLIC

        verify(exactly = 1) { module.provideRemoteConfig() }
    }

    @Test
    fun `given HttpLoggingInterceptor when isDev is true`() {
        val remoteConfig = mockk<RemoteConfig> {
            every { isDev() } returns true
        }
        val moduleModel = spyk<Module>()
        val httpLoggingInterceptorInstance = moduleModel.provideHttpLoggingInterceptor(config = remoteConfig)
        httpLoggingInterceptorInstance `should be instance of` HttpLoggingInterceptor::class.java
        httpLoggingInterceptorInstance.level `should equal` HttpLoggingInterceptor.Level.BODY

        verify(exactly = 1) { moduleModel.provideHttpLoggingInterceptor(any())}
    }

    @Test
    fun `verify provide Cache Method`() {
        val context = mockk<Context>{
            every { cacheDir } returns File("/data/data/mohsen.soltanian.coinmarketcaps/cache")
        }
        val module = spyk<Module>()
        val cacheOkHttp = module.provideOkHttpCache(context = context)

        cacheOkHttp `should be instance of` Cache::class.java
        cacheOkHttp.directory `should equal` File(context.cacheDir, "coin_market_cap_http-cache")

        verify(exactly = 1) { module.provideOkHttpCache(context = any()) }
    }

    @Test
    fun `verify provide OkHttp`() {
        val context = mockk<Context>{
            every { cacheDir } returns File("/data/data/mohsen.soltanian.coinmarketcaps/cache")
        }
        val remoteConfig = mockk<RemoteConfig> {
            every { isDev() } returns true
            every { timeOut() } returns 25L
        }

        val cache = mockk<Cache> {
            every { directory } returns File(context.cacheDir, "coin_market_cap_http-cache")
        }
        val module = spyk<Module>()
        val okHttpClient = module.provideOkHttpClient(config = remoteConfig, cache = cache)
        okHttpClient `should be instance of` OkHttpClient::class.java
        okHttpClient.connectTimeoutMillis `should equal` 25000
        okHttpClient.readTimeoutMillis `should equal` 25000
        okHttpClient.writeTimeoutMillis `should equal` 25000
        okHttpClient.interceptors.size `should equal` 2
        okHttpClient.cache `should be instance of` Cache::class.java
        okHttpClient.cache?.directory `should equal` File(
            context.cacheDir,
            "coin_market_cap_http-cache"
        )
        okHttpClient.followSslRedirects `should equal` true
        okHttpClient.followRedirects `should equal` true
        okHttpClient.retryOnConnectionFailure `should equal` true

        verify(exactly = 1) { module.provideOkHttpClient(config = any(), cache = any()) }

    }



    @Test
    fun `verify provide Moshi`() {
        val module = spyk<Module>()
        val moshi = module.provideMoshi()
        moshi `should be instance of` Moshi::class.java

        verify(exactly = 1) { module.provideMoshi() }
    }

    @Test
    fun `verify provide Retrofit `() {
        val remoteConfig = mockk<RemoteConfig> {
            every { baseUrl() } returns "https://pro-api.coinmarketcap.com/"
        }
        val okHttpClient = mockk<OkHttpClient>()
        val moshi = mockk<Moshi>()
        val module = spyk<Module>()
        val retrofit = module.provideRetrofit(config = remoteConfig,okHttpClient= okHttpClient,moshi= moshi)
        retrofit `should be instance of` Retrofit::class.java
        retrofit `should not equal` null

        verify(exactly = 1) { module.provideRetrofit(config = any(),okHttpClient= any(),moshi= any()) }
    }

}