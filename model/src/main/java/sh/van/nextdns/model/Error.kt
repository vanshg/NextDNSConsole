package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "error") val error: String?
)
