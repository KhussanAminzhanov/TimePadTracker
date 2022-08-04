package com.timepad.timepadtracker.presentation.screens.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timepad.timepadtracker.databinding.FragmentReportBinding
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportFragment : Fragment() {

    private val TAG = javaClass.simpleName

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    private val reportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()

        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                TimePadTheme {
                    ReportScreen(
                        reportViewModel = reportViewModel,
                        onBackArrowClick = ::onBackArrowClick
                    )
                }
            }
        }
    }

    private fun setupObservers() {
        reportViewModel.taskRecords.observe(viewLifecycleOwner) {
            reportViewModel.getTodayReport()
        }

        reportViewModel.allTaskRecords.observe(viewLifecycleOwner) {
            reportViewModel.getWeekReport()
        }
    }

    private fun onBackArrowClick() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}