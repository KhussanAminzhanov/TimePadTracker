package com.timepad.timepadtracker.presentation.screens.report

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentReportBinding
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.utils.getColorFromAttr
import com.timepad.timepadtracker.utils.getCurrentDayOfWeek
import com.timepad.timepadtracker.utils.getCurrentDaySinceEpoch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

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
        setupListeners()

        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                TimePadTheme {
                    ReportScreen(reportViewModel = reportViewModel)
                }
            }
        }
    }

    private fun setupObservers() {
        reportViewModel.selectedTab.observe(viewLifecycleOwner) {
            changeTabAppearance(it)
        }

        reportViewModel.taskRecords.observe(viewLifecycleOwner) { taskRecords ->
            val tasksCompleted = taskRecords.size
            var totalDuration: Long = 0

            taskRecords.forEach { totalDuration += it.duration }

            val hour = TimeUnit.MILLISECONDS.toHours(totalDuration)
            val minutes =
                TimeUnit.MILLISECONDS.toMinutes(totalDuration) - TimeUnit.HOURS.toMinutes(hour)

            binding.tvHour.text = hour.toString()
            binding.tvMinute.text = minutes.toString()
            binding.tvTasksCompletedCount.text = tasksCompleted.toString()

            reportViewModel.getTodayReport()
        }

        reportViewModel.allTaskRecords.observe(viewLifecycleOwner) { allTaskRecords ->
            reportViewModel.getWeekReport()
        }

        reportViewModel.todayReport.observe(viewLifecycleOwner) {
            Log.e(TAG, "Today's report: ${it.toList()}")
        }

        reportViewModel.weekReport.observe(viewLifecycleOwner) {
            Log.e(TAG, "Current day since epoch: ${getCurrentDaySinceEpoch()}")
            Log.e(TAG, "Current day of week: ${getCurrentDayOfWeek() - 1}")
            Log.e(TAG, "Week's report: ${it.toList()}")
        }
    }

    private fun changeTabAppearance(tabId: Int) {
        val tabs = listOf(binding.tvTabDay, binding.tvTabWeek)
        tabs.forEach {
            if (it.id == tabId) {
                it.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                it.setTextColor(requireContext().getColorFromAttr(android.R.attr.textColorTertiary))
            } else {
                it.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_light))
            }
        }
    }

    private fun setupListeners() {
        binding.tvTabDay.setOnClickListener { reportViewModel.setTab(it.id) }
        binding.tvTabWeek.setOnClickListener { reportViewModel.setTab(it.id) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}