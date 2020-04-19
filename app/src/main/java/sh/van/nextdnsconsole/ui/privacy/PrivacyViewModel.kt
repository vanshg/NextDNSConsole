package sh.van.nextdnsconsole.ui.privacy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrivacyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Privacy Fragment"
    }
    val text: LiveData<String> = _text
}
