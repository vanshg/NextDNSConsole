package sh.van.nextdnsconsole.ui.parentalcontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ParentalControlViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Parental Control Fragment"
    }
    val text: LiveData<String> = _text
}
