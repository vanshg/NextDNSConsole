package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class AccessControlListItem(
    @Json(name = "active") val active: Boolean?,
    @Json(name = "domain") val domain: String?
)
