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
import sh.van.nextdns.model.Settings
import sh.van.nextdnsconsole.App
import timber.log.Timber

class SettingsViewModel : ViewModel() {
    val settingsLiveData = MutableLiveData<Settings>()
    fun getSettings(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getSettings(configId)
                Timber.v("$response")
                settingsLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}


@Composable
fun SettingsScreen(settings: Settings?) = Column(modifier = Modifier.fillMaxWidth()) {
    if (settings == null) {
        LoadingIndicatorCentered()
        return@Column
    }
}
