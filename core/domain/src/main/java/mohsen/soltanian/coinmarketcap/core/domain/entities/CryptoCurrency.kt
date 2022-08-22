package mohsen.soltanian.coinmarketcap.core.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoCurrency(
    val id: Long?,
    val name: String?,
    val symbol: String?,
    val slug: String?,
    val price: Double?,
    val volume_change_24h: Double?,
    val imageUrl: String?,
    val marketCap: Double?
): Parcelable