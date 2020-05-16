package sh.van.nextdnsconsole.ui.parentalcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import sh.van.nextdnsconsole.databinding.FragmentParentalControlBinding

class ParentalControlFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parentalControlViewModel: ParentalControlViewModel by viewModels()
        val binding = FragmentParentalControlBinding.inflate(inflater, container, false)
        return binding.root
    }
}
