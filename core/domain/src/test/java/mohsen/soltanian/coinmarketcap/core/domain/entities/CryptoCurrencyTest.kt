package mohsen.soltanian.coinmarketcap.core.domain.entities

import io.mockk.every
import io.mockk.mockk
import mohsen.soltanian.coinmarketcap.core.data.extention.empty
import mohsen.soltanian.coinmarketcap.core.domain.core.DomainUnitTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

class CryptoCurrencyTest: DomainUnitTest() {

    @Test
    fun `verify CryptoCurrency Model via fake data`() {
        val model = mockk<CryptoCurrency>(relaxed = true)

        model `should be instance of` CryptoCurrency::class.java
        model.id `should equal` 0L
        model.name `should equal` String.empty()
        model.symbol `should equal` String.empty()
        model.slug `should equal` String.empty()
        model.price `should equal` 0.0
        model.volume_change_24h `should equal` 0.0
        model.imageUrl `should equal` String.empty()
        model.marketCap `should equal` 0.0

    }

    @Test
    fun `verify CryptoCurrency Model via real data`() {
        val model = mockk<CryptoCurrency>{
            every { id } returns 1L
            every { name } returns "Bitcoin"
            every { symbol } returns "BTC"
            every { slug } returns "bitcoin"
            every { price } returns 21391.136168706642
            every { volume_change_24h } returns -17.6025
            every { imageUrl } returns "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"
            every { marketCap } returns 409165224669.83374
        }


        model `should be instance of` CryptoCurrency::class.java
        model.id `should equal` 1L
        model.name `should equal` "Bitcoin"
        model.symbol `should equal` "BTC"
        model.slug `should equal` "bitcoin"
        model.price `should equal` 21391.136168706642
        model.volume_change_24h `should equal` -17.6025
        model.imageUrl `should equal` "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"
        model.marketCap `should equal` 409165224669.83374

    }
}