package mohsen.soltanian.coinmarketcap.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.coinmarketcap.core.domain.core.DomainUnitTest
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency
import org.amshove.kluent.`should be instance of`
import org.junit.Test

internal class CryptosVolumeFilterUseCaseTest: DomainUnitTest() {

    @MockK lateinit var uesCase: CryptosVolumeFilterUseCase

    @MockK(relaxed = true) lateinit var params: CryptosVolumeFilterUseCase.Params

    @Test
    fun `given cryptos list`() = runTest {
        coEvery { uesCase.invoke(params = any()) } returns flow {
            emit(value = PagingData.empty())
        }

        val response = uesCase.invoke(params = params)
        response.collect {
            it `should be instance of` PagingData::class.java
            it.map { model ->
                model `should be instance of` CryptoCurrency::class.java
            }
        }

        coVerify(exactly = 1) { uesCase.invoke(params = any()) }

    }

}