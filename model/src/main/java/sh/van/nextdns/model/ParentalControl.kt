package sh.van.nextdns.model

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ParentalControl(
    @Json(name = "block_bypass_methods") val blockBypassMethods: Boolean?,
    @Json(name = "categories") val categories: List<Any>?,
    @Json(name = "enforce_safesearch") val enforceSafesearch: Boolean?,
    @Json(name = "services") val services: List<Any>?,
    @Json(name = "youtube_restricted_mode") val youtubeRestrictedMode: Boolean?
)

@JsonClass(generateAdapter = true)
data class RestrictableService(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "website") val website: String?
)

enum class ServiceCategory(id: String) {
    Porn("porn"),
    Gambling("gambling"),
    Dating("dating"),
    Piracy("piracy"),
    SocialNetworks("social-networks"),
}

@JsonClass(generateAdapter = true)
data class Active(@Json(name = "active") val active: Boolean?)
