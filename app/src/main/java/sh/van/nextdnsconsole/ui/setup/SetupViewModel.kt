package sh.van.nextdnsconsole.ui.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdns.model.Setup
import timber.log.Timber

class SetupViewModel : ViewModel() {

    private val _setup = MutableLiveData<Setup>()
    val setup: LiveData<Setup> = _setup

    fun getSetup(service: NextDNSService) {
        viewModelScope.launch {
            val configId = "13d18c"
            try {
                _setup.value = service.getSetup(configId)
                Timber.d("Got: %s", service.getAccount())
                Timber.d("Got: %s", service.getLogs(configId))
                Timber.d("Got: %s", service.getCounters(configId))
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
