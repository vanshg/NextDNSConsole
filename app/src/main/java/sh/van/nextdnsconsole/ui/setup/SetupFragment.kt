package sh.van.nextdnsconsole.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.compose.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment.Companion.CenterHorizontally
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.core.setViewContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import sh.van.nextdns.model.Setup
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.R
import sh.van.nextdnsconsole.databinding.FragmentSetupBinding
import timber.log.Timber


class SetupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val setupViewModel: SetupViewModel by viewModels()
        val binding = FragmentSetupBinding.inflate(inflater, container, false)
        setupViewModel.getSetup(App.instance.service)
        binding.root.setContent(Recomposer.current()) {
            SetupScreenLiveDataComponent(setupViewModel.setup)
        }
        return binding.root
    }
}

@Composable
fun SetupScreenLiveDataComponent(setupLiveData: LiveData<Setup>) {
    val setup by setupLiveData.observeAsState()
    if (setup != null) SetupScreen(setup = setup!!)
    else LoadingIndicatorCentered()
}

@Composable
fun SetupScreen(setup: Setup) {
    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            Section(
                title = R.string.label_endpoints,
                subtitle = R.string.label_endpoints_subtitle
            ) {
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

            Section(
                title = R.string.label_linked_ip,
                subtitle = R.string.label_linked_ip_subtitle
            ) {
                Field(name = R.string.label_dns_servers, value = setup.linkedIpDNSServers)
                Field(name = R.string.label_linked_ip, value = setup.linkedIp)
                Field(name = R.string.label_ddns, value = setup.ddnsHostname)
            }
        }
    }
}

@Composable
fun Section(
    @StringRes title: Int,
    @StringRes subtitle: Int? = null,
    children: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth() + Modifier.padding(8.dp), elevation = 4.dp) {
        Column(modifier = Modifier.fillMaxWidth() + Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = stringResource(id = subtitle),
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            children()
        }
    }
}

@Composable
fun Field(@StringRes name: Int, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth() + Modifier.padding(top = 6.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = name), style = MaterialTheme.typography.subtitle2)
        Text(text = value.orEmpty(), style = MaterialTheme.typography.body1)
    }
}

@Composable
fun Field(@StringRes name: Int, value: List<String>?) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = stringResource(id = name), style = MaterialTheme.typography.subtitle2)
        Column {
            value?.forEach {
                Text(text = it, style = MaterialTheme.typography.body1)
            }
        }
    }
}

@Preview
@Composable
fun LoadingIndicatorCentered() {
    Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally))
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