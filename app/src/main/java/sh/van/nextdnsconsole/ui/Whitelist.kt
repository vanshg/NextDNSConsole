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

class WhitelistFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: WhitelistViewModel by viewModels()
        viewModel.getWhitelist(App.instance.service)
        WhitelistScreenLiveDataComponent(viewModel.whitelist)
    }
}

class WhitelistViewModel : ViewModel() {
    val whitelist = MutableLiveData<List<AccessControlListItem>>()
    fun getWhitelist(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getWhitelist(configId)
                Timber.v("$response")
                whitelist.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun WhitelistScreenLiveDataComponent(whitelistLiveData: LiveData<List<AccessControlListItem>>) {
    val whitelist by whitelistLiveData.observeAsState()
    if (whitelist == null) LoadingIndicatorCentered()
    else WhitelistScreen()
}

@Composable
fun WhitelistScreen() = Screen {

}

@Preview
@Composable
fun PreviewWhitelistScreen() {
    WhitelistScreen()
}
