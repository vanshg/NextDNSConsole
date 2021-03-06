package sh.van.nextdnsconsole.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import sh.van.nextdnsconsole.R
import sh.van.nextdnsconsole.databinding.FragmentLoginBinding
import sh.van.nextdnsconsole.ui.login.LoginViewModel.AuthenticationState.*
import timber.log.Timber

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel: LoginViewModel by viewModels()
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        val navController = findNavController()

        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                Authenticated -> {
                    navController.navigate(R.id.nav_setup)
                    hideKeyboard()
                }
                else -> Timber.d("Auth State: $it")
            }
        })

        loginViewModel.loginError.observe(viewLifecycleOwner, Observer {
            if (it.email != null) binding.emailInputLayout.error = it.email
            if (it.password != null) binding.passwordInputLayout.error = it.password
        })

        binding.loginButton.setOnClickListener {
            binding.passwordInputLayout.error = null
            binding.emailInputLayout.error = null
            loginViewModel.authenticate(
                binding.emailInputLayout.editText!!.text.toString(),
                binding.passwordInputLayout.editText!!.text.toString()
            )
        }
        loginViewModel.checkAuthState()
        return binding.root
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}