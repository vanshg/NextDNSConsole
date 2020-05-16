package sh.van.nextdnsconsole.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sh.van.nextdns.model.LoginErrors
import sh.van.nextdns.model.LoginRequest
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.ui.login.LoginViewModel.AuthenticationState.*
import timber.log.Timber

class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        Unauthenticated, Authenticated, InvalidAuthentication
    }

    val authenticationState = MutableLiveData<AuthenticationState>().apply {
        value = Unauthenticated
    }

    val loginError = MutableLiveData<LoginErrors>()

    fun authenticate(context: Context, email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginResponse = App.instance.service.login(LoginRequest(email, password))
                if (loginResponse.success) {
                    // Log in succeeded, token was set in the cookie jar
                    authenticationState.value = Authenticated
                } else {
                    authenticationState.value = InvalidAuthentication
                    loginError.value = loginResponse.errors
                    Timber.d(loginResponse.toString())
                }
            } catch (e: Exception) {
                Timber.e(e)
                authenticationState.value = InvalidAuthentication
            }
        }
    }
}
