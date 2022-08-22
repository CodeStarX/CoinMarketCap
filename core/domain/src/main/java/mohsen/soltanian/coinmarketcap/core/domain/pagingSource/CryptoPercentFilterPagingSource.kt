package mohsen.soltanian.coinmarketcap.core.domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import mohsen.soltanian.coinmarketcap.core.data.implClasses.ServiceImp
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency
import mohsen.soltanian.coinmarketcap.core.domain.extentions.toContract
import mohsen.soltanian.coinmarketcap.core.domain.usecase.CryptosPercentFilterUseCase
import java.io.IOException

class CryptoPercentFilterPagingSource(
    private val serviceImp: ServiceImp,
    private val filterParams: CryptosPercentFilterUseCase.Params
) : PagingSource<Int, CryptoCurrency>() {

    override fun getRefreshKey(state: PagingState<Int, CryptoCurrency>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoCurrency> {
        val page = params.key ?: 1
        return try {
            val response = serviceImp.getCryptosByPercentFilter(start = page.toString(), min = filterParams.percentMin, max = filterParams.percentMax)
            if(response.isSuccessful) {
                var currencies: List<CryptoCurrency>
                val ids = response.body()?.data?.map { it.id }
                val request = ids?.joinToString(separator = ",")
                val infoData = request?.let { req ->serviceImp.getCryptoInfo(id = req) }
                if(infoData?.isSuccessful == true) {
                    val imageUrls = infoData.body()?.data.orEmpty().values.associate { it.id to it.logo }
                    currencies = response.body()?.data!!
                        .map { currency ->
                            currency.toContract(imageUrls[currency.id]) }

                    LoadResult.Page(
                        data = currencies,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (currencies.isEmpty()) null else page + 1
                    )
                }else {
                    if(response.code() == 429) {
                        return LoadResult.Error(RuntimeException("You've exceeded your API Key's HTTP request rate limit. Rate limits reset every minute."))
                    }else
                        return LoadResult.Error(RuntimeException("something was wrong"))
                }

            }else {
                if(response.code() == 429) {
                    return LoadResult.Error(RuntimeException("You've exceeded your API Key's HTTP request rate limit. Rate limits reset every minute."))
                }else
                    return LoadResult.Error(RuntimeException("something was wrong"))
            }

        } catch (exception: IOException) {
            // IOException for network failures.
            return LoadResult.Error(exception)
        }
    }
}
