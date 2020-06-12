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
import sh.van.nextdns.model.ParentalControl
import sh.van.nextdnsconsole.App
import timber.log.Timber

class ParentalControlViewModel : ViewModel() {
    val parentalControlLiveData = MutableLiveData<ParentalControl>()
    fun getParentalControl(service: NextDNSService) {
        viewModelScope.launch {
            val configId = App.instance.selectedConfig
            try {
                val response = service.getParentalControl(configId)
                Timber.v("$response")
                parentalControlLiveData.value = response
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}

@Composable
fun ParentalControlScreen(parentalControl: ParentalControl?) =
    Column(modifier = Modifier.fillMaxWidth()) {
        if (parentalControl == null) {
            LoadingIndicatorCentered()
            return@Column
        }
    }