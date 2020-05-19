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
import sh.van.nextdns.model.Privacy
import sh.van.nextdnsconsole.App
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
            val configId = "13d18c"
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
    else PrivacyScreen()
}

@Composable
fun PrivacyScreen() = Screen {

}

@Preview
@Composable
fun PreviewPrivacyScreen() {
    PrivacyScreen()
}
