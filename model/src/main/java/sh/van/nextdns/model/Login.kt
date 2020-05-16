package sh.van.nextdns.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    // On login success, we set this value to true (API simply returns an empty response, which
    // Retrofit doesn't like)
    @Transient val success: Boolean = false,
    @Json(name = "errors") val errors: LoginErrors? = null
)

@JsonClass(generateAdapter = true)
data class LoginErrors(
    @Json(name = "email") val email: String?,
    @Json(name = "password") val password: String?
)
