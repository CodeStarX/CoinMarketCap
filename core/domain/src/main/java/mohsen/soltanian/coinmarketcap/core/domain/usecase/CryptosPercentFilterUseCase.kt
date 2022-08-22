package mohsen.soltanian.coinmarketcap.core.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mohsen.soltanian.coinmarketcap.core.data.implClasses.ServiceImp
import mohsen.soltanian.coinmarketcap.core.domain.base.BasePagingUseCase
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency
import mohsen.soltanian.coinmarketcap.core.domain.pagingSource.CryptoPercentFilterPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptosPercentFilterUseCase @Inject constructor(
    private val serviceImp: ServiceImp
): BasePagingUseCase<CryptosPercentFilterUseCase.Params, CryptoCurrency>() {

    data class Params(val pagingConfig: PagingConfig, val percentMin: Int, val percentMax: Int)

    override fun execute(params: Params): Flow<PagingData<CryptoCurrency>> {
        return Pager(config = params.pagingConfig,
            pagingSourceFactory = { CryptoPercentFilterPagingSource(serviceImp= serviceImp, filterParams = params) }
        ).flow
    }
}