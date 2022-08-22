package mohsen.soltanian.coinmarketcap.core.data.models.response

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import mohsen.soltanian.coinmarketcap.core.data.core.ModelTesting
import mohsen.soltanian.coinmarketcap.core.data.core.ModelUnitTest
import org.amshove.kluent.`should equal`
import org.junit.Test

@ModelTesting(
    clazz = CryptoInfoResponse::class,
    modelFields = ["id", "logo"])
class CryptoInfoResponseModelTest : ModelUnitTest() {

    @MockK
    lateinit var model: CryptoInfoResponse

    @Test
    fun `verify CryptoInfoResponse Model`() {
        every { model.id } returns 1L
        every { model.logo } returns "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"

        model.id `should equal` 1L
        model.logo `should equal` "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"

        verify(exactly = 1) {
            model.id
            model.logo
        }

    }
}