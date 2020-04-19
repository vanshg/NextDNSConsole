package sh.van.nextdns.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class AddDDNSRequest(
    @Json(name = "linkedip_ddns") val linkedipDdns: String
)
