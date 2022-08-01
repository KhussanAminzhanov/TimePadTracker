package com.timepad.timepadtracker.presentation.report

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.R.attr.textFillColor
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentReportBinding
import com.timepad.timepadtracker.utils.getColorFromAttr
import com.timepad.timepadtracker.utils.getCurrentDayOfWeek
import com.timepad.timepadtracker.utils.getCurrentDaySinceEpoch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class ReportFragment : Fragment() {

    private val TAG = javaClass.simpleName

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModel()

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
    }

    private fun setupObservers() {
        viewModel.selectedTab.observe(viewLifecycleOwner) {
            changeTabAppearance(it)
        }

        viewModel.taskRecords.observe(viewLifecycleOwner) { taskRecords ->
            val tasksCompleted = taskRecords.size
            var totalDuration: Long = 0

            taskRecords.forEach { totalDuration += it.duration }

            val hour = TimeUnit.MILLISECONDS.toHours(totalDuration)
            val minutes =
                TimeUnit.MILLISECONDS.toMinutes(totalDuration) - TimeUnit.HOURS.toMinutes(hour)

            binding.tvHour.text = hour.toString()
            binding.tvMinute.text = minutes.toString()
            binding.tvTasksCompletedCount.text = tasksCompleted.toString()

            viewModel.getTodayReport()
        }

        viewModel.allTaskRecords.observe(viewLifecycleOwner) { allTaskRecords ->
            viewModel.getWeekReport()
        }

        viewModel.todayReport.observe(viewLifecycleOwner) {
            Log.e(TAG, "Today's report: ${it.toList()}")
        }

        viewModel.weekReport.observe(viewLifecycleOwner) {
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
                it.setTextColor(R.color.text_light)
            }
        }
    }

    private fun setupListeners() {
        binding.tvTabDay.setOnClickListener { viewModel.setTab(it.id) }
        binding.tvTabWeek.setOnClickListener { viewModel.setTab(it.id) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}