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
import sh.van.nextdns.model.Security
import sh.van.nextdnsconsole.App
import timber.log.Timber

class SecurityFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: SecurityViewModel by viewModels()
        viewModel.getSecurity(App.instance.service)
        SecurityScreenLiveDataComponent(viewModel.security)
    }
}

class SecurityViewModel : ViewModel() {
    val security = MutableLiveData<Security>()
    fun getSecurity(service: NextDNSService) {
        viewModelScope.launch {
            val configId = "13d18c"
            try {
                val response = service.getSecurity(configId)
                Timber.v("$response")
                security.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun SecurityScreenLiveDataComponent(securityLiveData: LiveData<Security>) {
    val security by securityLiveData.observeAsState()
    if (security == null) LoadingIndicatorCentered()
    else SecurityScreen()
}

@Composable
fun SecurityScreen() = Screen {

}

@Preview
@Composable
fun PreviewSecurityScreen() {
    SecurityScreen()
}
