package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.livedata.observeAsState
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.Setup
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.R
import timber.log.Timber

class SetupFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: SetupViewModel by viewModels()
        viewModel.getSetup(App.instance.service)
        SetupScreenLiveDataComponent(viewModel.setup)
    }
}

class SetupViewModel : ViewModel() {
    val setup = MutableLiveData<Setup>()
    fun getSetup(service: NextDNSService) {
        viewModelScope.launch {
            val configId = "13d18c"
            try {
                val response = service.getSetup(configId)
                Timber.v("$response")
                setup.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun SetupScreenLiveDataComponent(setupLiveData: LiveData<Setup>) {
    val setup by setupLiveData.observeAsState()
    if (setup == null) LoadingIndicatorCentered()
    else SetupScreen(setup = setup!!)
}

@Composable
fun SetupScreen(setup: Setup) = Screen {
    Section(title = R.string.label_endpoints, subtitle = R.string.label_endpoints_subtitle) {
        Field(name = R.string.label_id, value = setup.id)
        Field(
            name = R.string.label_dns_over_tls,
            value = stringResource(R.string.dns_over_tls, setup.id!!)
        )
        Field(
            name = R.string.label_dns_over_https,
            value = stringResource(R.string.dns_over_https, setup.id!!)
        )
        Field(name = R.string.label_ipv6, value = setup.ipv6)
    }

    Section(title = R.string.label_linked_ip, subtitle = R.string.label_linked_ip_subtitle) {
        Field(name = R.string.label_dns_servers, value = setup.linkedIpDNSServers)
        Field(name = R.string.label_linked_ip, value = setup.linkedIp)
        Field(name = R.string.label_ddns, value = setup.ddnsHostname)
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
