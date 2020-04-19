package sh.van.nextdnsconsole.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import sh.van.nextdns.api.NextDNSService
import sh.van.nextdnsconsole.databinding.FragmentSetupBinding
import timber.log.Timber


class SetupFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val setupViewModel: SetupViewModel by viewModels()
        val binding = FragmentSetupBinding.inflate(inflater, container, false)
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            requireContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        setupViewModel.setup.observe(viewLifecycleOwner, Observer { setup ->
            binding.id.text = setup.id
            Timber.d("Got %s", setup)
        })

        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(sharedPreferences))
        val service = NextDNSService.get(cookieJar)
        setupViewModel.getSetup(service)
        return binding.root
    }
}
