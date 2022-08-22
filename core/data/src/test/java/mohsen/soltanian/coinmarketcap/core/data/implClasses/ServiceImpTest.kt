package mohsen.soltanian.coinmarketcap.core.data.implClasses

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.coinmarketcap.core.data.core.DataUnitTest
import mohsen.soltanian.coinmarketcap.core.data.models.response.CryptoInfoResponse
import mohsen.soltanian.coinmarketcap.core.data.models.response.CryptosResponse
import mohsen.soltanian.coinmarketcap.core.data.network.BaseResponse
import mohsen.soltanian.coinmarketcap.core.data.network.StatusResponse
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test
import retrofit2.Response

class ServiceImpTest : DataUnitTest() {

    @MockK
    lateinit var serviceImp: ServiceImp

    @Test
    fun `get cryptos list`() = runTest {
       val responseSUT = ResponseCryptoSUT()
        val retrofitResponse = mockk<Response<BaseResponse<List<CryptosResponse>>>> {
            every { isSuccessful } returns true
            every { body() } returns responseSUT
        }
        coEvery { serviceImp.getCryptos(start = any()) } returns retrofitResponse
         val response = serviceImp.getCryptos(start = "1").body()
        val cryptosResponse = response?.data?.get(0)
        retrofitResponse `should be instance of` Response::class.java
        retrofitResponse.isSuccessful `should equal` true
        retrofitResponse.body() `should be instance of` BaseResponse::class.java


        response?.status `should be instance of` StatusResponse::class.java
        response?.data `should be instance of` List::class.java

        cryptosResponse `should be instance of` CryptosResponse::class.java
        cryptosResponse?.id `should equal` 1L

        coVerify { serviceImp.getCryptos(start = any()) }
    }

    @Test
    fun `get crypto info`() = runTest() {
        val responseSUT = ResponseCryptoInfoSUT()
        val retrofitResponse = mockk<Response<BaseResponse<Map<String,CryptoInfoResponse>>>> {
            every { isSuccessful } returns true
            every { body() } returns responseSUT
        }
        coEvery { serviceImp.getCryptoInfo(id = any()) } returns retrofitResponse
        val response = serviceImp.getCryptoInfo(id = "1").body()
        val cryptoInfoResponse = response?.data
        retrofitResponse `should be instance of` Response::class.java
        retrofitResponse.isSuccessful `should equal` true
        retrofitResponse.body() `should be instance of` BaseResponse::class.java


        response?.status `should be instance of` StatusResponse::class.java
        response?.data `should be instance of` Map::class.java
        response?.data?.size `should equal` 1L

        cryptoInfoResponse.orEmpty().values.size `should equal` 1
        cryptoInfoResponse.orEmpty()["1"] `should be instance of`  CryptoInfoResponse::class.java
        cryptoInfoResponse.orEmpty()["1"]?.id `should equal` 1L
        cryptoInfoResponse.orEmpty()["1"]?.logo `should equal` "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"

        coVerify(exactly = 1) { serviceImp.getCryptoInfo(id = any()) }

    }

    val USDModel = CryptosResponse.USD(
        last_updated = "2022-08-21T14:35:00.000Z",
        market_cap = 409165224669.83374,
        percent_change_24h = 0.45237005,
        price = 21391.136168706642,
        volume_24h = 25306648792.09798,
        volume_change_24h = -17.6025
    )

    val quoteModel = CryptosResponse.Quote(USD = USDModel)
    val remoteResponse = CryptosResponse(
        id = 1L,
        name = "Bitcoin",
        quote = quoteModel,
        symbol = "BTC",
        slug = "bitcoin"
    )
    val status = StatusResponse(
        timestamp = "",
        errorCode = 0,
        errorMessage = "",
        elapsed = 0,
        creditCount = 0
    )

    val cryptoInfo = CryptoInfoResponse(
        id = 1L, logo = "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"
    )

    val map: Map<String, CryptoInfoResponse> = mapOf("1" to cryptoInfo)

    inner class ResponseCryptoSUT : BaseResponse<List<CryptosResponse>>(data = listOf(remoteResponse), status = status)

    inner class ResponseCryptoInfoSUT : BaseResponse<Map<String,CryptoInfoResponse>>(data = map, status = status)

}