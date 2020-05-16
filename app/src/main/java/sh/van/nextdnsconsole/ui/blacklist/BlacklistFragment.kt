package sh.van.nextdnsconsole.ui.blacklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import sh.van.nextdnsconsole.databinding.FragmentBlacklistBinding

class BlacklistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val blacklistViewModel: BlacklistViewModel by viewModels()
        val binding = FragmentBlacklistBinding.inflate(inflater, container, false)
        return binding.root
    }
}
