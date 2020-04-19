package sh.van.nextdnsconsole.ui.security

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecurityViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Security Fragment"
    }
    val text: LiveData<String> = _text
}
