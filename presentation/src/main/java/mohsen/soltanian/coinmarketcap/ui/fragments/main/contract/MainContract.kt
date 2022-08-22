package mohsen.soltanian.coinmarketcap.ui.fragments.main.contract

import androidx.paging.PagingData
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency

class MainContract {
    sealed class Event {
        object fetchCryptos: Event()
        object fetchCryptosBySort: Event()
        object fetchCryptosByPercentFilter: Event()
        object fetchCryptosByVolumeFilter: Event()
    }

    sealed class State {
        data class CryptosData(
            val pagedData: PagingData<CryptoCurrency> =
                PagingData.empty()
        ) : State()
    }
}