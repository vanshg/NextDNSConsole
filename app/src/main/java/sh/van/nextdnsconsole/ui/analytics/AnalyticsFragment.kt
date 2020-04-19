package sh.van.nextdnsconsole.ui.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import sh.van.nextdnsconsole.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val analyticsViewModel: AnalyticsViewModel by viewModels()
        val binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
