package sh.van.nextdnsconsole.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import sh.van.nextdnsconsole.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val settingsViewModel: SettingsViewModel by viewModels()
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
