package com.timepad.timepadtracker.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentMainBinding
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.findTopNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val newTaskBottomSheet = NewTaskBottomSheet()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.composeViewMain.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                TimePadTheme {
                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        MainScreen(
                            mainViewModel = mainViewModel,
                            onAddItemClick = ::onAddItemClick,
                            onTaskItemClick = ::onTaskItemClick,
                            onSeeAllClick = ::onSeeAllClick,
                            onRightArrowClick = ::onRightArrowClick
                        )
                    }
                }
            }
        }
    }

    private fun onAddItemClick() {
        newTaskBottomSheet.show(childFragmentManager, null)
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