package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.BlendMode
import androidx.ui.graphics.Color
import androidx.ui.graphics.ColorFilter
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.livedata.observeAsState
import androidx.ui.material.Button
import androidx.ui.material.IconButton
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.Security
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.R
import timber.log.Timber

class SecurityFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: SecurityViewModel by viewModels()
        viewModel.getSecurity(App.instance.service)
        SecurityScreenLiveDataComponent(viewModel)
    }
}

class SecurityViewModel : ViewModel() {
    val securityLiveData = MutableLiveData<Security>()
    fun getSecurity(service: NextDNSService) {
        viewModelScope.launch {
            val configId = "13d18c"
            try {
                val response = service.getSecurity(configId)
                Timber.v("$response")
                securityLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun setSecurity(security: Security, service: NextDNSService) {
        securityLiveData.value = security
        val configId = "13d18c"
        viewModelScope.launch {
            try {
                val result = service.updateSecurity(configId, security)
                Timber.d(result)
                securityLiveData.value = service.getSecurity(configId)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    }
}

@Composable
fun SecurityScreenLiveDataComponent(securityViewModel: SecurityViewModel) {
    val security by securityViewModel.securityLiveData.observeAsState()
    if (security == null) LoadingIndicatorCentered()
    else SecurityScreen(security!!, securityViewModel)
}

@Composable
fun SecurityScreen(
    security: Security,
    viewModel: SecurityViewModel
) = Screen {
    SingleItemToggleSection(
        R.string.label_threat_intelligence_feeds,
        R.string.label_threat_intelligence_feeds_subtitle,
        R.string.label_use_threat_intelligence_feeds,
        security.tiFeeds ?: false
    )

    SingleItemToggleSection(
        R.string.label_google_safe_browsing,
        R.string.label_google_safe_browsing_subtitle,
        R.string.label_enable_google_safe_browsing,
        security.googleSafeBrowsing ?: false
    )

    SingleItemToggleSection(
        R.string.label_cryptojacking_protection,
        R.string.label_cryptojacking_protection_subtitle,
        R.string.label_enable_cryptojacking_protection,
        security.cryptojackingProtection ?: false
    )

    SingleItemToggleSection(
        R.string.label_dns_rebinding_protection,
        R.string.label_dns_rebinding_protection_subtitle,
        R.string.label_enable_dns_rebinding_protection,
        security.dnsRebindingProtection ?: false
    )

    SingleItemToggleSection(
        R.string.label_idn_homograph_attacks_protection,
        R.string.label_idn_homograph_attacks_protection_subtitle,
        R.string.label_enable_idn_homograph_attacks_protection,
        security.idnHomographAttacksProtection ?: false
    )

    SingleItemToggleSection(
        R.string.label_typosquatting_protection,
        R.string.label_typosquatting_protection_subtitle,
        R.string.label_enable_typosquatting_protection,
        security.typosquattingProtection ?: false
    )

    SingleItemToggleSection(
        R.string.label_dga_protection,
        R.string.label_dga_protection_subtitle,
        R.string.label_enable_dga_protection,
        security.dgaProtection ?: false
    ) {
        Timber.e("EHLLOOE")
        viewModel.setSecurity(security.copy(dgaProtection = it), App.instance.service)
    }

    SingleItemToggleSection(
        R.string.label_block_nrds,
        R.string.label_block_nrds_subtitle,
        R.string.label_block_nrds,
        security.blockNrd ?: false
    )

    SingleItemToggleSection(
        R.string.label_block_parked_domains,
        R.string.label_block_parked_domains_subtitle,
        R.string.label_block_parked_domains,
        security.blockParkedDomains ?: false
    )

    Section(R.string.label_block_tlds, R.string.label_block_tlds_subtitle) {
        security.blockedTlds?.forEach {
            var text = when {
                !it.unicode.isNullOrEmpty() -> it.unicode!!
                !it.tld.isNullOrEmpty() -> it.tld!!
                else -> ""
            }
            if (!it.description.isNullOrEmpty()) {
                // TODO: Make the parenthesized part a different color
                text = "$text (${it.description})"
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text)
                IconButton(onClick = { Timber.e("TODO: Remove TLD") }) {
                    Image(
                        asset = vectorResource(R.drawable.ic_delete), colorFilter = ColorFilter(
                            Color.Black, BlendMode.srcATop
                        )
                    )
                }
            }
        }
        Button(onClick = { Timber.e("TODO: Add a TLD") }) {
            Text(stringResource(R.string.label_add_a_tld))
        }
    }

    SingleItemToggleSection(
        R.string.label_block_csam,
        R.string.label_block_csam_subtitle,
        R.string.label_block_csam,
        security.blockCsam ?: false,
        R.string.label_block_csam_subtitle2
    )

}

//@Preview
//@Composable
//fun PreviewSecurityScreen() {
//    SecurityScreen()
//}
