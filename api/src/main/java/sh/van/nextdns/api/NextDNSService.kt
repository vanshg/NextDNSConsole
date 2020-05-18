package sh.van.nextdns.api

import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import sh.van.nextdns.model.*

interface NextDNSService {
    companion object {
        private lateinit var service: NextDNSService
        fun get(
            cookieJar: CookieJar,
            clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        ): NextDNSService {
            if (!::service.isInitialized) {
                service = Retrofit.Builder()
                    .client(clientBuilder.cookieJar(cookieJar).build())
                    .baseUrl("https://api.nextdns.io")
                    .addConverterFactory(LoginSuccessConverterFactory())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                    .create(NextDNSService::class.java)

            }
            return service
        }
    }

    @POST("/accounts/@login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/accounts/@me")
    suspend fun getProfile(
        @Query("withConfigurations") withConfigurations: Boolean = true
    ): Profile

    @GET("/account")
    suspend fun getAccount(): Account

    @GET("/configurations/{configId}/setup")
    suspend fun getSetup(@Path("configId") configId: String): Setup

    @PATCH("/configurations/{configId}/setup")
    suspend fun addDDNS(
        @Path("configId") configId: String,
        @Body addDDNSRequest: AddDDNSRequest
    ): Setup

    @GET("/configurations/{configId}/security")
    suspend fun getSecurity(@Path("configId") configId: String): Security

    @PATCH("/configurations/{configId}/security")
    suspend fun updateSecurity(
        @Path("configId") configId: String,
        @Body updatedSecurity: Security
    ): String

    @GET("/configurations/{configId}/security/blocked_tlds/@all")
    suspend fun getBlockableTLDs(@Path("configId") configId: String): BlockableTLDs

    @GET("/configurations/{configId}/privacy")
    suspend fun getPrivacy(@Path("configId") configId: String): Privacy

    @GET("/configurations/{configId}/privacy/blocklists/@all")
    suspend fun getAllAvailableBlocklists(@Path("configId") configId: String): List<Blocklist>

    @PUT("/configurations/{configId}/privacy/blocklists/hex:{hexEncodedId}")
    suspend fun addBlocklist(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @DELETE("/configurations/{configId}/privacy/blocklists/hex:{hexEncodedId}")
    suspend fun removeBlocklist(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @GET("/configurations/{configId}/parentalcontrol")
    suspend fun getParentalControl(@Path("configId") configId: String): ParentalControl

    @PATCH("/configurations/{configId}/parentalcontrol")
    suspend fun updateParentalControl(@Path("configId") configId: String): ParentalControl

    @GET("/configurations/{configId}/parentalcontrol/services/@all")
    suspend fun getRestrictableServices(
        @Path("configId") configId: String
    ): List<RestrictableService>

    @PUT("/configurations/{configId}/parentalcontrol/services/hex:{hexEncodedId}")
    suspend fun addRestrictedService(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @DELETE("/configurations/{configId}/parentalcontrol/services/hex:{hexEncodedId}")
    suspend fun removeRestrictedService(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @PATCH("/configurations/{configId}/parentalcontrol/services/hex:{hexEncodedId}")
    suspend fun setRestrictedServiceActive(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String,
        @Body active: Active
    ): String

    @PUT("/configurations/{configId}/parentalcontrol/categories/hex:{hexEncodedId}")
    suspend fun addRestrictedServiceCategory(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @DELETE("/configurations/{configId}/parentalcontrol/categories/hex:{hexEncodedId}")
    suspend fun removeRestrictedServiceCategory(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @PATCH("/configurations/{configId}/parentalcontrol/categories/hex:{hexEncodedId}")
    suspend fun setRestrictedServiceCategoryActive(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String,
        @Body active: Active
    ): String

    @GET("/configurations/{configId}/blacklist")
    suspend fun getBlacklist(@Path("configId") configId: String): List<AccessControlListItem>

    @PUT("/configurations/{configId}/blacklist/hex:{hexEncodedId}")
    suspend fun addBlacklistItem(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): AccessControlListItem

    @PATCH("/configurations/{configId}/blacklist/hex:{hexEncodedId}")
    suspend fun setBlacklistItemActive(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String,
        @Body active: Active
    ): String

    @DELETE("/configurations/{configId}/blacklist/hex:{hexEncodedId}")
    suspend fun removeBlacklistItem(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @GET("/configurations/{configId}/whitelist")
    suspend fun getWhitelist(@Path("configId") configId: String): List<AccessControlListItem>

    @PUT("/configurations/{configId}/whitelist/hex:{hexEncodedId}")
    suspend fun addWhitelistItem(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): AccessControlListItem

    @PATCH("/configurations/{configId}/whitelist/hex:{hexEncodedId}")
    suspend fun setWhitelistItemActive(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String,
        @Body active: Active
    ): String

    @DELETE("/configurations/{configId}/whitelist/hex:{hexEncodedId}")
    suspend fun removeWhitelistItem(
        @Path("configId") configId: String,
        @Path("hexEncodedId") hexEncodedId: String
    ): String

    @GET("/configurations/{configId}/analytics/counters")
    suspend fun getCounters(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): Counters

    @GET("/configurations/{configId}/analytics/resolved")
    suspend fun getTopResolvedDomains(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<DomainStats>

    @GET("/configurations/{configId}/analytics/blocked")
    suspend fun getTopBlockedDomains(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<DomainStats>

    @GET("/configurations/{configId}/analytics/top_lists")
    suspend fun getTopBlockedReasons(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<BlocklistStats>

    @GET("/configurations/{configId}/analytics/top_devices")
    suspend fun getTopDevices(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<DeviceStats>

    @GET("/configurations/{configId}/analytics/top_client_ips")
    suspend fun getClientIPAnalytics(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<ClientIPStats>

    @GET("/configurations/{configId}/analytics/top_root_domains")
    suspend fun getTopRootDomains(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<DomainStats>

    @GET("/configurations/{configId}/analytics/secure_dns")
    suspend fun getSecureDNSStats(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): SecureDNSStats

    @GET("/configurations/{configId}/analytics/dnssec")
    suspend fun getDNSSECStats(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): DNSSECStats

    @GET("/configurations/{configId}/analytics/queries_chart")
    suspend fun getQueriesChart(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<QueriesChartDataPoint>

    @GET("/configurations/{configId}/analytics/gafam")
    suspend fun getGAFAM(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): List<CompanyStats>

    @GET("/configurations/{configId}/analytics/traffic_destination_countries")
    suspend fun getTrafficDestinationCountries(
        @Path("configId") configId: String,
        @Query("from") from: String? = null,
        @Query("timezoneOffset") timezoneOffset: Int? = null
    ): Map<String, CountryStats>

    @GET("/configurations/{configId}/logs")
    suspend fun getLogs(
        @Path("configId") configId: String,
        @Query("search") search: String? = null,
        @Query("before") before: Long? = null,
        @Query("after") after: Long? = null,
        @Query("blockedQueriesOnly") blockedQueriesOnly: Boolean? = null
    ): Logs

    @DELETE("/configurations/{configId}/logs")
    suspend fun clearLogs(
        @Path("configId") configId: String
    ): String

    @GET("/configurations/{configId}/settings")
    suspend fun getSettings(@Path("configId") configId: String): Settings

    @PATCH("/configurations/{configId}/settings")
    suspend fun updateSettings(
        @Path("configId") configId: String,
        @Body settings: Settings
    ): String

    @POST("/configurations/{configId}/settings/rewrites")
    suspend fun addRewrite(
        @Path("configId") configId: String,
        @Body rewrite: Rewrite
    ): Rewrite

    @DELETE("/configurations/{configId}/settings/rewrites/{rewriteId}")
    suspend fun removeRewrite(
        @Path("configId") configId: String,
        @Path("rewriteId") rewriteId: String
    ): String

    @DELETE("/configurations/{configId}")
    suspend fun deleteConfiguration(@Path("configId") configId: String): String


    // This should be handled within the app by an image loading lib
//    @GET("https://favicons.nextdns.io/hex:{hexEncodedId}@2x.png")
//    suspend fun getFaviconForService(@Path("hexEncodedId") hexEncodedId: String)
}
