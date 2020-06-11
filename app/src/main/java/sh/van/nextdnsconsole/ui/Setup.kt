package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.Setup
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.R
import timber.log.Timber

class SetupViewModel : ViewModel() {
    val setupLiveData = MutableLiveData<Setup>()
    fun getSetup(service: NextDNSService) {
        viewModelScope.launch {
            try {
                val configId = App.instance.selectedConfig
                val response = service.getSetup(configId)
                Timber.v("$response")
                setupLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun SetupScreen(setup: Setup?) = Screen {
    if (setup == null) {
        LoadingIndicatorCentered()
        return@Screen
    }
    Section(title = R.string.label_endpoints, subtitle = R.string.label_endpoints_subtitle) {
        TextItem(name = R.string.label_id, value = setup.id)
        TextItem(
            name = R.string.label_dns_over_tls,
            value = stringResource(R.string.dns_over_tls, setup.id!!)
        )
        TextItem(
            name = R.string.label_dns_over_https,
            value = stringResource(R.string.dns_over_https, setup.id!!)
        )
        TextItem(name = R.string.label_ipv6, value = setup.ipv6)
    }

    Section(title = R.string.label_linked_ip, subtitle = R.string.label_linked_ip_subtitle) {
        TextItem(name = R.string.label_dns_servers, value = setup.linkedIpDNSServers)
        TextItem(name = R.string.label_linked_ip, value = setup.linkedIp)
        TextItem(name = R.string.label_ddns, value = setup.ddnsHostname)
    }

}

@Preview
@Composable
fun PreviewSetupScreen() {
    SetupScreen(
        Setup(
            apiKey = "foobar",
            ddnsHostname = null,
            dnsStamp = "sdns://foobar",
            fingerprint = "foobar",
            id = "foobar",
            ipv4 = listOf(),
            ipv6 = listOf("foo", "bar"),
            ipv6Expanded = listOf(
                "foo",
                "bar"
            ),
            linkedIp = "foo.bar",
            linkedIpDNSServers = listOf("f.o.o", "b.a.r")
        )
    )
}
