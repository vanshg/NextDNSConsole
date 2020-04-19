package sh.van.nextdns.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class Profile(
    @Json(name = "configurations") val configurations: List<Configuration>?,
    @Json(name = "email") val email: String?
)

data class Configuration(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?
)

@JsonClass(generateAdapter = true)
data class Account(
    @Json(name = "business") val business: Business?,
    @Json(name = "email") val email: String?,
    @Json(name = "invoices") val invoices: List<String>?,
    @Json(name = "prices") val prices: Prices?,
    @Json(name = "subscription") val subscription: String?,
    @Json(name = "subscriptionBeta") val subscriptionBeta: Boolean?
)

@JsonClass(generateAdapter = true)
data class Business(
    @Json(name = "address") val address: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "vatNumber") val vatNumber: String?
)

@JsonClass(generateAdapter = true)
data class Prices(
    @Json(name = "currency") val currency: String?,
    @Json(name = "plans") val plans: Plans?
)

@JsonClass(generateAdapter = true)
data class Plans(
    @Json(name = "business") val business: BusinessX?,
    @Json(name = "pro") val pro: Pro?
)

@JsonClass(generateAdapter = true)
data class BusinessX(
    @Json(name = "month") val month: Double?,
    @Json(name = "year") val year: Int?
)

@JsonClass(generateAdapter = true)
data class Pro(
    @Json(name = "month") val month: Double?,
    @Json(name = "year") val year: Double?
)
