package sh.van.nextdnsconsole.ui.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import sh.van.nextdnsconsole.databinding.FragmentPrivacyBinding

class PrivacyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val privacyViewModel: PrivacyViewModel by viewModels()
        val binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        return binding.root
    }
}
