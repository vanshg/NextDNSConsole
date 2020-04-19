package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Privacy(
    @Json(name = "allow_affiliate_links") val allowAffiliateLinks: Boolean?,
    @Json(name = "block_disguised_trackers") val blockDisguisedTrackers: Boolean?,
    @Json(name = "blocklists") val blocklists: List<Blocklist>?
)

@JsonClass(generateAdapter = true)
data class Blocklist(
    @Json(name = "description") val description: String?,
    @Json(name = "entries") val entries: Int?,
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "updated_time") val updatedTime: Int?,
    @Json(name = "website") val website: String?
)
