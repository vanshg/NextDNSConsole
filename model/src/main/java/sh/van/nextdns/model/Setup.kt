package sh.van.nextdns.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Setup(
    @Json(name = "apiKey") val apiKey: String?,
    @Json(name = "ddnsHostname") val ddnsHostname: String?,
    @Json(name = "dnsStamp") val dnsStamp: String?,
    @Json(name = "fingerprint") val fingerprint: String?,
    @Json(name = "id") val id: String?,
    @Json(name = "ipv4") val ipv4: List<String>?,
    @Json(name = "ipv6") val ipv6: List<String>?,
    @Json(name = "ipv6Expanded") val ipv6Expanded: List<String>?,
    @Json(name = "linkedIp") val linkedIp: String?,
    @Json(name = "linkedIpDNSServers") val linkedIpDNSServers: List<String>?
)
