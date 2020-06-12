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
import sh.van.nextdns.model.Logs
import sh.van.nextdnsconsole.App
import timber.log.Timber

class LogsViewModel : ViewModel() {
    val logsLiveData = MutableLiveData<Logs>()
    fun getLogs(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getLogs(configId)
                Timber.v("$response")
                logsLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun LogsScreen(logs: Logs?) = Column(modifier = Modifier.fillMaxWidth()) {
    if (logs == null) {
        LoadingIndicatorCentered()
        return@Column
    }
}