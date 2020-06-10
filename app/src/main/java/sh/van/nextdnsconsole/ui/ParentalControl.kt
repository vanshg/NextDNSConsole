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
import sh.van.nextdns.model.ParentalControl
import sh.van.nextdnsconsole.App
import timber.log.Timber

class ParentalControlFragment : BaseFragment() {
    @Composable
    override fun initialize() {
        val viewModel: ParentalControlViewModel by viewModels()
        viewModel.getParentalControl(App.instance.service)
        ParentalControlScreenLiveDataComponent(viewModel.parentalControl)
    }
}

class ParentalControlViewModel : ViewModel() {
    val parentalControl = MutableLiveData<ParentalControl>()
    fun getParentalControl(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getParentalControl(configId)
                Timber.v("$response")
                parentalControl.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun ParentalControlScreenLiveDataComponent(parentalControlLiveData: LiveData<ParentalControl>) {
    val parentalControl by parentalControlLiveData.observeAsState()
    if (parentalControl == null) LoadingIndicatorCentered()
    else ParentalControlScreen()
}

@Composable
fun ParentalControlScreen() = Screen {

}

@Preview
@Composable
fun PreviewParentalControlScreen() {
    ParentalControlScreen()
}
