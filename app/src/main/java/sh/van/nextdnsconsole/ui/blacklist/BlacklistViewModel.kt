package sh.van.nextdnsconsole.ui.blacklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlacklistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Blacklist Fragment"
    }
    val text: LiveData<String> = _text
}
