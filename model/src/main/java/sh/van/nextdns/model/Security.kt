package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Security(
    @Json(name = "block_csam") val blockCsam: Boolean?,
    @Json(name = "block_nrd") val blockNrd: Boolean?,
    @Json(name = "block_parked_domains") val blockParkedDomains: Boolean?,
    @Json(name = "blocked_tlds") val blockedTlds: List<TLD>?,
    @Json(name = "cryptojacking_protection") val cryptojackingProtection: Boolean?,
    @Json(name = "dga_protection") val dgaProtection: Boolean?,
    @Json(name = "dns_rebinding_protection") val dnsRebindingProtection: Boolean?,
    @Json(name = "google_safe_browsing") val googleSafeBrowsing: Boolean?,
    @Json(name = "idn_homograph_attacks_protection") val idnHomographAttacksProtection: Boolean?,
    @Json(name = "ti_feeds") val tiFeeds: Boolean?,
    @Json(name = "typosquatting_protection") val typosquattingProtection: Boolean?
)

@JsonClass(generateAdapter = true)
data class BlockableTLDs(
    @Json(name = "all") val all: List<TLD>?,
    @Json(name = "commonlyBlocked") val commonlyBlocked: List<TLD>?
)

@JsonClass(generateAdapter = true)
data class TLD(
    @Json(name = "description") val description: String?,
    @Json(name = "tld") val tld: String?,
    @Json(name = "unicode") val unicode: String?
)
