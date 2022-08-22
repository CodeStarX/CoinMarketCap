package mohsen.soltanian.coinmarketcap.ui.fragments.main

import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosPercentFilterUseCase
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosSortUseCase
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosUseCase
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosVolumeFilterUseCase
import mohsen.soltanian.coinmarketcap.enums.MenuActionType
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.mvi.MviViewModel
import mohsen.soltanian.coinmarketcap.ui.fragments.main.contract.MainContract
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cryptosUseCase: CryptosUseCase,
    private val cryptosSortUseCase: CryptosSortUseCase,
    private val cryptosPercentFilterUseCase: CryptosPercentFilterUseCase,
    private val cryptosVolumeFilterUseCase: CryptosVolumeFilterUseCase
) : MviViewModel<MainContract.State, MainContract.Event>() {

    var actionType: MenuActionType = MenuActionType.NONE
    private val config = PagingConfig(pageSize = 20)

    var percentMin = ""
    var percentMax = ""

    var volumeMin = ""
    var volumeMax = ""

    override fun onTriggerEvent(eventType: MainContract.Event) {
        when (eventType) {
            is MainContract.Event.fetchCryptos -> {
                fetchCryptoData()
            }
            is MainContract.Event.fetchCryptosBySort -> {
                fetchCryptoBySortData()
            }
            is MainContract.Event.fetchCryptosByPercentFilter -> {
                fetchCryptoByPercentFilterData()
            }
            is MainContract.Event.fetchCryptosByVolumeFilter -> {
                fetchCryptoByVolumeFilterData()
            }
        }
    }


    private fun fetchCryptoData() = safeLaunch {
        val params = CryptosUseCase.Params(pagingConfig = config)
        callWithProgress(callFlow = cryptosUseCase(params = params), completionHandler = {
            setState(MainContract.State.CryptosData(it))
        })
    }

    private fun fetchCryptoBySortData() = safeLaunch {
        val params = CryptosSortUseCase.Params(pagingConfig = config,
            sorType = actionType.type
        )
        callWithProgress(callFlow = cryptosSortUseCase(params = params), completionHandler = {
            setState(MainContract.State.CryptosData(it))
        })
    }

    private fun fetchCryptoByPercentFilterData() = safeLaunch {
        val params = CryptosPercentFilterUseCase.Params(pagingConfig = config,
            percentMin = percentMin.toInt(),
            percentMax = percentMax.toInt()
        )
        callWithProgress(callFlow = cryptosPercentFilterUseCase(params = params), completionHandler = {
            setState(MainContract.State.CryptosData(it))
        })
    }

    private fun fetchCryptoByVolumeFilterData() = safeLaunch {
        val params = CryptosVolumeFilterUseCase.Params(pagingConfig = config,
            volumeMin = volumeMin.toInt(),
            volumeMax = volumeMax.toInt()
        )
        callWithProgress(callFlow = cryptosVolumeFilterUseCase(params = params), completionHandler = {
            setState(MainContract.State.CryptosData(it))
        })
    }

}