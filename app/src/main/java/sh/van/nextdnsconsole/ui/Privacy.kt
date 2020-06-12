package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.Privacy
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.R
import timber.log.Timber

class PrivacyViewModel : ViewModel() {
    val privacyLiveData = MutableLiveData<Privacy>()
    fun getPrivacy(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getPrivacy(configId)
                Timber.v("$response")
                privacyLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun PrivacyScreen(privacy: Privacy?) = Column(modifier = Modifier.fillMaxWidth()) {
    if (privacy == null) {
        LoadingIndicatorCentered()
        return@Column
    }
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
