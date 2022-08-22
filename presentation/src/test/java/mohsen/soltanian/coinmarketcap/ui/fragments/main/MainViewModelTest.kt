package mohsen.soltanian.coinmarketcap.ui.fragments.main

import androidx.paging.PagingData
import androidx.paging.map
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.coinmarketcap.core.AppUnitTest
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosPercentFilterUseCase
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosSortUseCase
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosUseCase
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosVolumeFilterUseCase
import mohsen.soltanian.coinmarketcap.ui.fragments.main.contract.MainContract
import org.amshove.kluent.`should be instance of`
import org.junit.Test

class MainViewModelTest: AppUnitTest() {

    @MockK lateinit var cryptosUseCase: CryptosUseCase
    @MockK lateinit var cryptosSortUseCase: CryptosSortUseCase
    @MockK lateinit var cryptosPercentFilterUseCase: CryptosPercentFilterUseCase
    @MockK lateinit var cryptosVolumeFilterUseCase: CryptosVolumeFilterUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: MainViewModel

    @Test
    fun `verify onTriggerEvent method`() {
        viewModel.onTriggerEvent(eventType = MainContract.Event.fetchCryptos)
        verify(exactly = 1) { viewModel.onTriggerEvent(any())}
    }

    @Test
    fun `fetch cryptos Data`() = runTest {
        val params = mockk<CryptosUseCase.Params>(relaxed = true)
        every { cryptosUseCase.invoke(params = any()) } returns flow {
            emit(value = PagingData.empty())
        }

        viewModel.onTriggerEvent(eventType = MainContract.Event.fetchCryptos)
        cryptosUseCase.invoke(params = params).collect {
            it `should be instance of` PagingData::class.java
            it.map { model ->
                model `should be instance of` CryptoCurrency::class.java
            }
        }

        verify {
            viewModel.onTriggerEvent(eventType = any())
            cryptosUseCase.invoke(params = any())
        }
    }

    @Test
    fun `fetch Cryptos By Sort`() = runTest {
        val params = mockk<CryptosSortUseCase.Params>(relaxed = true)
        coEvery { cryptosSortUseCase.invoke(params = any()) } returns flow {
            emit(value = PagingData.empty())
        }

        viewModel.onTriggerEvent(eventType = MainContract.Event.fetchCryptosBySort)
        cryptosSortUseCase.invoke(params= params).collect {
            it `should be instance of` PagingData::class.java
            it.map { model ->
                model `should be instance of` CryptoCurrency::class.java
            }
        }

        coVerify  {
            viewModel.onTriggerEvent(eventType = any())
            cryptosSortUseCase.invoke(params = any())
        }
    }

    @Test
    fun `fetch Cryptos By Percent Filter`() = runTest {
        val params = mockk<CryptosPercentFilterUseCase.Params>(relaxed = true)
        coEvery { cryptosPercentFilterUseCase.invoke(params = any()) } returns flow {
            emit(value = PagingData.empty())
        }

        viewModel.onTriggerEvent(eventType = MainContract.Event.fetchCryptosBySort)
        cryptosPercentFilterUseCase.invoke(params= params).collect {
            it `should be instance of` PagingData::class.java
            it.map { model ->
                model `should be instance of` CryptoCurrency::class.java
            }
        }

        coVerify  {
            viewModel.onTriggerEvent(eventType = any())
            cryptosPercentFilterUseCase.invoke(params = any())
        }
    }


    @Test
    fun `fetch Cryptos By Volume Filter`() = runTest {
        val params = mockk<CryptosVolumeFilterUseCase.Params>(relaxed = true)
        coEvery { cryptosVolumeFilterUseCase.invoke(params = any()) } returns flow {
            emit(value = PagingData.empty())
        }

        viewModel.onTriggerEvent(eventType = MainContract.Event.fetchCryptosBySort)
        cryptosVolumeFilterUseCase.invoke(params= params).collect {
            it `should be instance of` PagingData::class.java
            it.map { model ->
                model `should be instance of` CryptoCurrency::class.java
            }
        }

        coVerify  {
            viewModel.onTriggerEvent(eventType = any())
            cryptosVolumeFilterUseCase.invoke(params = any())
        }
    }


}