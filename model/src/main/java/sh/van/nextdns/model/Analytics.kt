package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Counters(
    @Json(name = "blockedQueries") val blockedQueries: Int?,
    @Json(name = "queries") val queries: Int?
)

@JsonClass(generateAdapter = true)
data class DeviceStats(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "queries") val queries: Int?
)

@JsonClass(generateAdapter = true)
data class DomainStats(
    @Json(name = "name") val name: String?,
    @Json(name = "queries") val queries: Int?
)

@JsonClass(generateAdapter = true)
data class BlocklistStats(
    @Json(name = "name") val name: String?,
    @Json(name = "queries") val queries: Int?
)

@JsonClass(generateAdapter = true)
data class ClientIPStats(
    @Json(name = "city") val city: String?,
    @Json(name = "country") val country: String?,
    @Json(name = "countryCode") val countryCode: String?,
    @Json(name = "ip") val ip: String?,
    @Json(name = "isCellular") val isCellular: Boolean?,
    @Json(name = "isp") val isp: String?,
    @Json(name = "queries") val queries: Int?
)

@JsonClass(generateAdapter = true)
data class SecureDNSStats(
    @Json(name = "secure") val secure: Int?,
    @Json(name = "total") val total: Int?
)

@JsonClass(generateAdapter = true)
data class DNSSECStats(
    @Json(name = "dnssec") val dnssec: Int?,
    @Json(name = "total") val total: Int?
)

@JsonClass(generateAdapter = true)
data class QueriesChartDataPoint(
    @Json(name = "blockedQueries") val blockedQueries: Int?,
    @Json(name = "dayOnly") val dayOnly: Boolean?,
    @Json(name = "name") val dateInEpochSeconds: Int?,
    @Json(name = "queries") val queries: Int?
)

@JsonClass(generateAdapter = true)
data class CompanyStats(
    @Json(name = "company") val company: String?,
    @Json(name = "percentage") val percentage: Double?,
    @Json(name = "queries") val queries: Int?
)

@JsonClass(generateAdapter = true)
data class CountryStats(
    @Json(name = "queries") val queries: Int?,
    @Json(name = "topDomains") val topDomains: List<String>?
)

