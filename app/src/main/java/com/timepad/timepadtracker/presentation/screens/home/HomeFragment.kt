package com.timepad.timepadtracker.presentation.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentHomeBinding
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.findTopNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                TimePadTheme {
                    HomeScreen(
                        onTaskItemClick = ::onTaskItemClick,
                        onSeeAllClick = ::onSeeAllClick,
                        onRightArrowClick = ::onRightArrowClick
                    )
                }
            }
        }
    }

    private fun onRightArrowClick() {
        findTopNavController().navigate(R.id.timerFragment)
    }

    private fun onSeeAllClick() {
        findTopNavController().navigate(R.id.historyFragment)
    }

    private fun onTaskItemClick(task: Task) {
        findTopNavController().navigate(R.id.timerFragment)
        mainViewModel.setSelectedTask(task)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}