package sh.van.nextdnsconsole.ui.logs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import sh.van.nextdnsconsole.databinding.FragmentLogsBinding

class LogsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val logsViewModel: LogsViewModel by viewModels()
        val binding = FragmentLogsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
