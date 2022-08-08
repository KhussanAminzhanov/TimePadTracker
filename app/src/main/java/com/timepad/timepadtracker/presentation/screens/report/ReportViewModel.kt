package com.timepad.timepadtracker.presentation.screens.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timepad.timepadtracker.domain.TaskRecord
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.utils.getCurrentDayOfWeek
import com.timepad.timepadtracker.utils.getCurrentDaySinceEpoch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ReportViewModel(
    private val interactions: Interactions,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _selectedTab = MutableLiveData<String>()
    val selectedTab: LiveData<String> = _selectedTab

    val taskRecords = interactions.getTaskRecordsByDay(getCurrentDaySinceEpoch())
    val allTaskRecords = interactions.getAllTaskRecords()

    fun setTab(tab: String) {
        _selectedTab.value = tab
    }

    fun getTodayReport(records: List<TaskRecord>): List<Long> {
        val list = MutableList<Long>(24) { 0 }
        records.forEach { record ->
            list[record.hour] += record.duration
        }
        Log.e("ReportViewModel", "day report: $list")
        return list
    }

    fun getWeekReport(records: List<TaskRecord>): List<Long> {
        val list = MutableList<Long>(7) { 0 }
        val epoch = getCurrentDaySinceEpoch()
        val dayOfWeek = getCurrentDayOfWeek() - 1
        repeat(list.size) { index ->
            val day = epoch - (dayOfWeek - index - 1)
            val totalDuration = records.filter { it.epochDay == day }.sumOf { it.duration }
            list[index] = totalDuration
        }
        Log.e("ReportViewModel", "week report: $list")
        return list
    }
}