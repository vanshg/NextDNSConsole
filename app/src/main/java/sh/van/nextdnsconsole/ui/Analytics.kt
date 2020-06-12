package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.core.Modifier
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.*
import sh.van.nextdnsconsole.App
import timber.log.Timber

class AnalyticsFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: AnalyticsViewModel by viewModels()
        AnalyticsScreenLiveDataComponent(
            viewModel.counters,
            viewModel.topResolvedDomains,
            viewModel.topBlockedDomains,
            viewModel.topBlockedReasons,
            viewModel.topDevices,
            viewModel.clientIpAnalytics,
            viewModel.topRootDomains,
            viewModel.secureDnsStats,
            viewModel.dnsSecStats,
            viewModel.companyStats,
            viewModel.queriesChart,
            viewModel.trafficDestinationCountries
        )
    }
}

class AnalyticsViewModel : ViewModel() {
    val counters = MutableLiveData<Counters>()
    val topResolvedDomains = MutableLiveData<List<DomainStats>>()
    val topBlockedDomains = MutableLiveData<List<DomainStats>>()
    val topBlockedReasons = MutableLiveData<List<BlocklistStats>>()
    val topDevices = MutableLiveData<List<DeviceStats>>()
    val clientIpAnalytics = MutableLiveData<List<ClientIPStats>>()
    val topRootDomains = MutableLiveData<List<DomainStats>>()
    val secureDnsStats = MutableLiveData<SecureDNSStats>()
    val dnsSecStats = MutableLiveData<DNSSECStats>()
    val companyStats = MutableLiveData<List<CompanyStats>>()
    val queriesChart = MutableLiveData<List<QueriesChartDataPoint>>()
    val trafficDestinationCountries = MutableLiveData<Map<String, CountryStats>>()
    fun loadData(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                counters.value = service.getCounters(configId)
                topResolvedDomains.value = service.getTopResolvedDomains(configId)
                topBlockedDomains.value = service.getTopBlockedDomains(configId)
                topBlockedReasons.value = service.getTopBlockedReasons(configId)
                topDevices.value = service.getTopDevices(configId)
                clientIpAnalytics.value = service.getClientIPAnalytics(configId)
                topRootDomains.value = service.getTopRootDomains(configId)
                secureDnsStats.value = service.getSecureDNSStats(configId)
                dnsSecStats.value = service.getDNSSECStats(configId)
                companyStats.value = service.getGAFAM(configId)
                queriesChart.value = service.getQueriesChart(configId)
                trafficDestinationCountries.value = service.getTrafficDestinationCountries(configId)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun AnalyticsScreenLiveDataComponent(
    countersLiveData: MutableLiveData<Counters>,
    topResolvedDomainsLiveData: MutableLiveData<List<DomainStats>>,
    topBlockedDomainsLiveData: MutableLiveData<List<DomainStats>>,
    topBlockedReasonsLiveData: MutableLiveData<List<BlocklistStats>>,
    topDevicesLiveData: MutableLiveData<List<DeviceStats>>,
    clientIpAnalyticsLiveData: MutableLiveData<List<ClientIPStats>>,
    topRootDomainsLiveData: MutableLiveData<List<DomainStats>>,
    secureDnsStatsLiveData: MutableLiveData<SecureDNSStats>,
    dnsSecStatsLiveData: MutableLiveData<DNSSECStats>,
    companyStatsLiveData: MutableLiveData<List<CompanyStats>>,
    queriesChartLiveData: MutableLiveData<List<QueriesChartDataPoint>>,
    trafficDestinationCountriesLiveData: MutableLiveData<Map<String, CountryStats>>
) {
    val counters by countersLiveData.observeAsState()
    val topResolvedDomains by topResolvedDomainsLiveData.observeAsState()
    val topBlockedDomains by topBlockedDomainsLiveData.observeAsState()
    val topBlockedReasons by topBlockedReasonsLiveData.observeAsState()
    val topDevices by topDevicesLiveData.observeAsState()
    val clientIpAnalytics by clientIpAnalyticsLiveData.observeAsState()
    val topRootDomains by topRootDomainsLiveData.observeAsState()
    val secureDnsStats by secureDnsStatsLiveData.observeAsState()
    val dnsSecStats by dnsSecStatsLiveData.observeAsState()
    val companyStats by companyStatsLiveData.observeAsState()
    val queriesChart by queriesChartLiveData.observeAsState()
    val trafficDestinationCountries by trafficDestinationCountriesLiveData.observeAsState()
//    if (analytics == null) LoadingIndicatorCentered()
//    else AnalyticsScreen()
}

@Composable
fun AnalyticsScreen() = Column(modifier = Modifier.fillMaxWidth()) {
    if (null == null) {
        LoadingIndicatorCentered()
        return@Column
    }
}

@Preview
@Composable
fun PreviewAnalyticsScreen() {
    AnalyticsScreen()
}
