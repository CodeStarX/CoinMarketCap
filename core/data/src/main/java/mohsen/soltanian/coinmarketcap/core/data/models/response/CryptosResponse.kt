package mohsen.soltanian.coinmarketcap.core.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptosResponse(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "quote")
    val quote: Quote?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "symbol")
    val symbol: String?
) {
    @JsonClass(generateAdapter = true)
    data class Quote(
        @Json(name = "USD")
        val USD: USD?
    )

    @JsonClass(generateAdapter = true)
    data class USD(
        @Json(name = "last_updated")
        val last_updated: String?,
        @Json(name = "market_cap")
        val market_cap: Double?,
        @Json(name = "percent_change_24h")
        val percent_change_24h: Double?,
        @Json(name = "price")
        val price: Double?,
        @Json(name = "volume_24h")
        val volume_24h: Double?,
        @Json(name = "volume_change_24h")
        val volume_change_24h: Double?
    )
}
