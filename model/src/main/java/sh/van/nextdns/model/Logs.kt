package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Logs(
    @Json(name = "hasMore") val hasMore: Boolean?,
    @Json(name = "logs") val logs: List<Log>?
)

@JsonClass(generateAdapter = true)
data class Log(
    @Json(name = "clientIp") val clientIp: String?,
    @Json(name = "clientName") val clientName: String?,
    @Json(name = "deviceId") val deviceId: String?,
    @Json(name = "deviceName") val deviceName: String?,
    @Json(name = "dnssec") val dnssec: Boolean?,
    @Json(name = "isSecureDNS") val isSecureDNS: Boolean?,
    @Json(name = "lists") val lists: List<String>?,
    @Json(name = "name") val name: String?,
    @Json(name = "protocol") val protocol: String?,
    @Json(name = "rootDomainStartIndex") val rootDomainStartIndex: Int?,
    @Json(name = "status") val status: Int?,
    @Json(name = "timestamp") val timestamp: Long?,
    @Json(name = "tracker") val tracker: Tracker?,
    @Json(name = "type") val type: String?
)

@JsonClass(generateAdapter = true)
data class Tracker(
    @Json(name = "category") val category: String?,
    @Json(name = "company") val company: Company?,
    @Json(name = "name") val name: String?,
    @Json(name = "prevalence") val prevalence: Double?,
    @Json(name = "website") val website: String?
)

@JsonClass(generateAdapter = true)
data class Company(
    @Json(name = "description") val description: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "privacyURL") val privacyURL: String?
)
