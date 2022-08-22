package mohsen.soltanian.coinmarketcap.core.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BaseResponse<T>(
    @Json(name = "data")
    val data: T?,

    @Json(name = "status")
    val status: StatusResponse
)

data class StatusResponse(
    @Json(name = "timestamp")
    val timestamp: String,

    @Json(name = "error_code")
    val errorCode: Int?,

    @Json(name = "error_message")
    val errorMessage: String?,

    @Json(name = "elapsed")
    val elapsed: Int,

    @Json(name = "credit_count")
    val creditCount: Int
)