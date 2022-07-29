package com.timepad.timepadtracker.presentation.report

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.databinding.FragmentReportBinding
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
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            var tasksCompleted: Long = 0
            var totalDuration: Long = 0

            tasks.forEach { task ->
                tasksCompleted += task.totalTimeInMillis / task.oneSessionTime
                totalDuration += task.totalTimeInMillis
            }

            val hour = TimeUnit.MILLISECONDS.toHours(totalDuration)
            val minutes =
                TimeUnit.MILLISECONDS.toMinutes(totalDuration) - TimeUnit.HOURS.toMinutes(hour)

            binding.tvHour.text = hour.toString()
            binding.tvMinute.text = minutes.toString()
            binding.tvTasksCompletedCount.text = tasksCompleted.toString()
        }
    }

    private fun changeTabAppearance(tabId: Int) {
        val tabs = listOf(binding.tvTabDay, binding.tvTabWeek)
        tabs.forEach {
            if (it.id == tabId) {
                it.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            } else {
                it.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
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