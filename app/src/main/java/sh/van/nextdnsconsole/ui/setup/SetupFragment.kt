package sh.van.nextdnsconsole.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import sh.van.nextdnsconsole.App
import sh.van.nextdnsconsole.R
import sh.van.nextdnsconsole.databinding.FragmentSetupBinding
import timber.log.Timber


class SetupFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val setupViewModel: SetupViewModel by viewModels()
        val binding = FragmentSetupBinding.inflate(inflater, container, false)

        setupViewModel.setup.observe(viewLifecycleOwner, Observer { setup ->
            binding.id.text = setup.id
            binding.dnsOverTls.text = getString(R.string.dns_over_tls, setup.id)
            binding.dnsOverHttps.text = getString(R.string.dns_over_https, setup.id)
            Timber.d("Got %s", setup)
        })

        setupViewModel.getSetup(App.instance.service)
        return binding.root
    }
}
