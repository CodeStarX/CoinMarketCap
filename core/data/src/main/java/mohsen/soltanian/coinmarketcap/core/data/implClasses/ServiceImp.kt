package mohsen.soltanian.coinmarketcap.core.data.implClasses


import mohsen.soltanian.coinmarketcap.core.data.models.response.*
import mohsen.soltanian.coinmarketcap.core.data.network.BaseResponse
import mohsen.soltanian.coinmarketcap.core.data.scopes.ServerService
import mohsen.soltanian.coinmarketcap.core.data.services.RemoteApi
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceImp
@Inject constructor(
    @ServerService var retrofit: Retrofit) : RemoteApi {
    private val api by lazy { retrofit.create(RemoteApi::class.java) }

    override suspend fun getCryptos(start: String): Response<BaseResponse<List<CryptosResponse>>> =
        api.getCryptos(start= start)

    override suspend fun getCryptosBySort(start: String, sortType: String): Response<BaseResponse<List<CryptosResponse>>> =
        api.getCryptosBySort(start = start, sortType = sortType)

    override suspend fun getCryptosByPercentFilter(start: String, min: Int, max: Int): Response<BaseResponse<List<CryptosResponse>>> =
        api.getCryptosByPercentFilter(start = start, min = min, max= max)

    override suspend fun getCryptosByVolumeFilter(start: String, min: Int, max: Int): Response<BaseResponse<List<CryptosResponse>>> =
        api.getCryptosByVolumeFilter(start= start, min= min, max= max)

    override suspend fun getCryptoInfo(id: String): Response<BaseResponse<Map<String, CryptoInfoResponse>>> =
        api.getCryptoInfo(id = id)

}