package mohsen.soltanian.coinmarketcap.core.data.services


import mohsen.soltanian.coinmarketcap.core.data.models.response.*
import mohsen.soltanian.coinmarketcap.core.data.network.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {

    companion object {
        const val API_KEY = "035b0363-8219-4d43-8175-2144ad5d5d28"
    }

    @GET("v1/cryptocurrency/listings/latest?limit=20")
    suspend fun getCryptos(
        @Query("start") start: String
    ): Response<BaseResponse<List<CryptosResponse>>>

    @GET("v1/cryptocurrency/listings/latest?limit=20")
    suspend fun getCryptosBySort(
        @Query("start") start: String,
        @Query("sort") sortType: String
    ): Response<BaseResponse<List<CryptosResponse>>>

    @GET("v1/cryptocurrency/listings/latest?limit=20")
    suspend fun getCryptosByPercentFilter(
        @Query("start") start: String,
        @Query("percent_change_24h_min") min: Int,
        @Query("percent_change_24h_max") max: Int
    ): Response<BaseResponse<List<CryptosResponse>>>

    @GET("v1/cryptocurrency/listings/latest?limit=20")
    suspend fun getCryptosByVolumeFilter(
        @Query("start") start: String,
        @Query("volume_24h_min") min: Int,
        @Query("volume_24h_max") max: Int
    ): Response<BaseResponse<List<CryptosResponse>>>

    @GET("v1/cryptocurrency/info")
    suspend fun getCryptoInfo(
        @Query("id") id: String
    ) : Response<BaseResponse<Map<String, CryptoInfoResponse>>>

}