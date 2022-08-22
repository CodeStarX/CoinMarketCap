package mohsen.soltanian.coinmarketcap.core.data.network.interceptor

import mohsen.soltanian.coinmarketcap.core.data.services.RemoteApi.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class CoinMarketCapsRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader(name = "X-CMC_PRO_API_KEY", value = API_KEY).build()
        return chain.proceed(request)
    }
}