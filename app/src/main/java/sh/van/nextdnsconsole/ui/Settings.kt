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
import sh.van.nextdns.model.Settings
import sh.van.nextdnsconsole.App
import timber.log.Timber

class SettingsFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: SettingsViewModel by viewModels()
        viewModel.getSettings(App.instance.service)
        SettingsScreenLiveDataComponent(viewModel.settings)
    }
}

class SettingsViewModel : ViewModel() {
    val settings = MutableLiveData<Settings>()
    fun getSettings(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getSettings(configId)
                Timber.v("$response")
                settings.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun SettingsScreenLiveDataComponent(settingsLiveData: LiveData<Settings>) {
    val settings by settingsLiveData.observeAsState()
    if (settings == null) LoadingIndicatorCentered()
    else SettingsScreen()
}

@Composable
fun SettingsScreen() = Screen {

}

@Preview
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}
