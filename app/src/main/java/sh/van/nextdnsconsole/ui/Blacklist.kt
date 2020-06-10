package sh.van.nextdnsconsole.ui

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.AccessControlListItem
import sh.van.nextdnsconsole.App
import timber.log.Timber

class BlacklistFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: BlacklistViewModel by viewModels()
        viewModel.getBlacklist(App.instance.service)
        BlacklistScreenLiveDataComponent(viewModel.blacklist)
    }
}

class BlacklistViewModel : ViewModel() {
    val blacklist = MutableLiveData<List<AccessControlListItem>>()
    fun getBlacklist(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getBlacklist(configId)
                Timber.v("$response")
                blacklist.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun BlacklistScreenLiveDataComponent(blacklistLiveData: LiveData<List<AccessControlListItem>>) {
    val blacklist by blacklistLiveData.observeAsState()
    if (blacklist == null) LoadingIndicatorCentered()
    else BlacklistScreen()
}

@Composable
fun BlacklistScreen() = Screen {

}

@Preview
@Composable
fun PreviewBlacklistScreen() {
    BlacklistScreen()
}
