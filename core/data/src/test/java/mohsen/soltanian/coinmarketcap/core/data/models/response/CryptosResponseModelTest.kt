package mohsen.soltanian.coinmarketcap.core.data.models.response

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import mohsen.soltanian.coinmarketcap.core.data.core.ModelTesting
import mohsen.soltanian.coinmarketcap.core.data.core.ModelUnitTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

@ModelTesting(
    clazz = CryptosResponse::class,
    modelFields = ["id", "name", "quote", "slug", "symbol"])
class CryptosResponseModelTest : ModelUnitTest() {

    @Test
    fun `verify CryptosResponse Model`() {
       val USDModel = mockk<CryptosResponse.USD>(relaxed = true)
        val quoteModel = mockk<CryptosResponse.Quote>{
            every { USD } returns USDModel
        }
        val model = mockk<CryptosResponse>{
            every { id } returns 1L
            every { name } returns "Bitcoin"
            every { quote } returns quoteModel
            every { symbol } returns "BTC"
            every { slug } returns "bitcoin"
        }
        model `should be instance of` CryptosResponse::class.java
        model.id `should equal` 1L
        model.name `should equal` "Bitcoin"
        model.quote `should be instance of` CryptosResponse.Quote::class.java
        model.symbol `should equal` "BTC"
        model.slug `should equal` "bitcoin"

        verify(exactly = 1) {
            model.id
            model.name
            model.quote
            model.symbol
            model.slug
        }
    }
}