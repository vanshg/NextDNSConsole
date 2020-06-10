package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.livedata.observeAsState
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.Privacy
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.R
import timber.log.Timber

class PrivacyFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: PrivacyViewModel by viewModels()
        viewModel.getPrivacy(App.instance.service)
        PrivacyScreenLiveDataComponent(viewModel.privacy)
    }
}

class PrivacyViewModel : ViewModel() {
    val privacy = MutableLiveData<Privacy>()
    fun getPrivacy(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getPrivacy(configId)
                Timber.v("$response")
                privacy.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun PrivacyScreenLiveDataComponent(privacyLiveData: LiveData<Privacy>) {
    val privacy by privacyLiveData.observeAsState()
    if (privacy == null) LoadingIndicatorCentered()
    else PrivacyScreen(privacy!!)
}

@Composable
fun PrivacyScreen(privacy: Privacy) = Screen {
    DeletableItemListSection(
        R.string.label_blocklists,
        R.string.label_blocklists_subtitle,
        R.string.label_add_a_blocklist,
        privacy.blocklists,
        onAddButtonClick = { Timber.e("TODO: Add a Blocklist") },
        onDeleteButtonClick = { Timber.e("TODO: Remove a Blocklist") }
    ) {
        Text(it.name.orEmpty())
        Text(it.description.orEmpty())
        Row {
            // TODO: Proper separators between items
            Text(it.website.orEmpty()) // TODO: Link
            Text(it.entries.toString())
            Text(it.updatedTime.toString()) // TODO: Relative time
        }
    }

    DeletableItemListSection(
        R.string.label_native_tracking_protection,
        R.string.label_native_tracking_protection_subtitle,
        R.string.label_button_add,
        privacy.natives,
        onAddButtonClick = { Timber.e("TODO: Add a Blocklist") },
        onDeleteButtonClick = { Timber.e("TODO: Remove a Blocklist") }
    ) {
        Text(it.id.orEmpty())
    }

    SingleItemToggleSection(
        R.string.label_block_disguised_third_party_trackers,
        R.string.label_block_disguised_third_party_trackers_subtitle,
        R.string.label_block_disguised_third_party_trackers,
        privacy.blockDisguisedTrackers ?: false
    )

    SingleItemToggleSection(
        R.string.label_allow_affiliate_and_tracking_links,
        R.string.label_allow_affiliate_and_tracking_links_subtitle,
        R.string.label_allow_affiliate_and_tracking_links,
        privacy.allowAffiliateLinks ?: false
    )
}
