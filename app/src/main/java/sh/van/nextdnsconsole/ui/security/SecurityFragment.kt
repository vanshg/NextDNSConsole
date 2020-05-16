package sh.van.nextdnsconsole.ui.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import sh.van.nextdnsconsole.databinding.FragmentSecurityBinding

class SecurityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val securityViewModel: SecurityViewModel by viewModels()
        val binding = FragmentSecurityBinding.inflate(inflater, container, false)
        return binding.root
    }
}
