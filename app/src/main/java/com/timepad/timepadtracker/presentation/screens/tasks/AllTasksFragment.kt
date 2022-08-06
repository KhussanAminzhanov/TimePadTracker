package com.timepad.timepadtracker.presentation.screens.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentHistoryBinding
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.findTopNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllTasksFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.composeViewHistory.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                TimePadTheme {
                    AllTasksScreen(
                        mainViewModel = mainViewModel,
                        onTaskItemClick = ::onTaskItemClick,
                        onBackArrowClick = ::onBackArrowClick
                    )
                }
            }
        }
    }

    private fun onTaskItemClick(task: Task) {
        findNavController().popBackStack()
        findNavController().navigate(R.id.timerFragment)
        mainViewModel.setSelectedTask(task)
    }

    private fun onBackArrowClick() {
        findTopNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}