package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Settings(
    @Json(name = "block_page") val blockPage: Boolean?,
    @Json(name = "edns_client_subnet") val ednsClientSubnet: Boolean?,
    @Json(name = "handshake") val handshake: Boolean?,
    @Json(name = "logging") val logging: Boolean?,
    @Json(name = "logging_disable_client") val loggingDisableClient: Boolean?,
    @Json(name = "logging_disable_query") val loggingDisableQuery: Boolean?,
    @Json(name = "logging_location") val loggingLocation: String?,
    @Json(name = "logging_retention") val loggingRetention: Long?,
    @Json(name = "name") val name: String?,
    @Json(name = "rewrites") val rewrites: List<Rewrite>?
)

@JsonClass(generateAdapter = true)
data class Rewrite(
    @Json(name = "answer") val answer: String?,
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?
)

enum class LoggingLocation(id: String?) {
    EU("eu"), US(null), Switzerland("ch")
}
