package mohsen.soltanian.coinmarketcap.core.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoInfoResponse(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "logo")
    val logo: String?
)



