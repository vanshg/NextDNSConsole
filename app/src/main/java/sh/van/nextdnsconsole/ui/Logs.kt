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
import sh.van.nextdns.model.Logs
import sh.van.nextdnsconsole.App
import timber.log.Timber

class LogsFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: LogsViewModel by viewModels()
        viewModel.getLogs(App.instance.service)
        LogsScreenLiveDataComponent(viewModel.logs)
    }
}

class LogsViewModel : ViewModel() {
    val logs = MutableLiveData<Logs>()
    fun getLogs(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getLogs(configId)
                Timber.v("$response")
                logs.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun LogsScreenLiveDataComponent(logsLiveData: LiveData<Logs>) {
    val logs by logsLiveData.observeAsState()
    if (logs == null) LoadingIndicatorCentered()
    else LogsScreen()
}

@Composable
fun LogsScreen() = Screen {

}

@Preview
@Composable
fun PreviewLogsScreen() {
    LogsScreen()
}
