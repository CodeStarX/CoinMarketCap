package mohsen.soltanian.coinmarketcap.core.data.core

import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import io.mockk.MockKAnnotations
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
open class DataUnitTest2 {

    open fun onSetUpTest() {}

    open fun onStopTest() {}

    @Before
    fun onSetup() {
        MockKAnnotations.init(this)
        onSetUpTest()
    }

    @After
    fun onTearDown() {
        onStopTest()
    }
}