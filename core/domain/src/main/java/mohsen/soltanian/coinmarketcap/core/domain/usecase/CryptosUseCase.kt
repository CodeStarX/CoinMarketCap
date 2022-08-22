package mohsen.soltanian.coinmarketcap.core.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mohsen.soltanian.coinmarketcap.core.data.implClasses.ServiceImp
import mohsen.soltanian.coinmarketcap.core.domain.base.BasePagingUseCase
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency
import mohsen.soltanian.coinmarketcap.core.domain.pagingSource.CryptoPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptosUseCase @Inject constructor(
    private val serviceImp: ServiceImp
): BasePagingUseCase<CryptosUseCase.Params, CryptoCurrency>() {

    data class Params(val pagingConfig: PagingConfig)

    override fun execute(params: Params): Flow<PagingData<CryptoCurrency>> {
        return Pager(config = params.pagingConfig,
            pagingSourceFactory = { CryptoPagingSource(serviceImp= serviceImp) }
        ).flow
    }
}