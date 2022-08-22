package mohsen.soltanian.coinmarketcap.core.domain.extentions

import mohsen.soltanian.coinmarketcap.core.data.models.response.CryptosResponse
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency

fun CryptosResponse.toContract(imageUrl: String?) =
    CryptoCurrency(
        id = id,
        name = name,
        symbol = symbol,
        slug = slug,
        price= quote?.USD?.price,
        volume_change_24h = quote?.USD?.volume_change_24h,
        imageUrl = imageUrl,
        marketCap = quote?.USD?.market_cap
    )