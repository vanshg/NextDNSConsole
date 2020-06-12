package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.core.Modifier
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.AccessControlListItem
import sh.van.nextdnsconsole.App
import timber.log.Timber

class AccessControlListViewModel : ViewModel() {
    val accessControlListLiveData = MutableLiveData<List<AccessControlListItem>>()
    fun getBlacklist(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getBlacklist(configId)
                Timber.v("$response")
                accessControlListLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun getWhitelist(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getWhitelist(configId)
                Timber.v("$response")
                accessControlListLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun AccessControlListScreen(blacklist: List<AccessControlListItem>?) =
    Column(modifier = Modifier.fillMaxWidth()) {
        if (blacklist == null) {
            LoadingIndicatorCentered()
            return@Column
        }
    }