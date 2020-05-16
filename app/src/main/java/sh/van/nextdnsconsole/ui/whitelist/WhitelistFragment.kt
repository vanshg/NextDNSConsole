package sh.van.nextdnsconsole.ui.whitelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import sh.van.nextdnsconsole.databinding.FragmentWhitelistBinding

class WhitelistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val whitelistViewModel: WhitelistViewModel by viewModels()
        val binding = FragmentWhitelistBinding.inflate(inflater, container, false)
        return binding.root
    }
}
