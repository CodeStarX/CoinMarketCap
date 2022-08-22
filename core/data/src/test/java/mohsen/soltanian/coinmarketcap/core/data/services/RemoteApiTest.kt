package mohsen.soltanian.coinmarketcap.core.data.services

import kotlinx.coroutines.test.runTest
import mohsen.soltanian.coinmarketcap.core.data.core.ServicesUnitTest
import mohsen.soltanian.coinmarketcap.core.data.core.ServiceUnitTesting
import mohsen.soltanian.coinmarketcap.core.data.network.BaseResponse
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test
import retrofit2.Response

@ServiceUnitTesting(baseUrl = "https://pro-api.coinmarketcap.com/", clazz = RemoteApi::class)
internal class RemoteApiTest: ServicesUnitTest() {

    @Test
    fun `run test by live service`() = runTest {
        val response = serviceBuilder(isLive = true).getCryptos("1")
        val ob = response.body()
        response `should be instance of` Response::class.java
        response.body() `should be instance of` BaseResponse::class.java
        ob?.data `should be instance of` List::class.java
        ob?.data?.size `should equal` 20

    }

}