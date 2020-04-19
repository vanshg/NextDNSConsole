package sh.van.nextdnsconsole.ui.whitelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WhitelistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Whitelist Fragment"
    }
    val text: LiveData<String> = _text
}
