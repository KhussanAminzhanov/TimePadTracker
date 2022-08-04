package com.timepad.timepadtracker.presentation.screens.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _todayReport = MutableLiveData<LongArray>()
    val todayReport: LiveData<LongArray> = _todayReport

    private val _weekReport = MutableLiveData<LongArray>()
    val weekReport: LiveData<LongArray> = _weekReport

    fun setTab(tab: String) {
        _selectedTab.value = tab
    }

    fun getTodayReport() {
        val records = taskRecords.value ?: return
        val list = LongArray(24) { 0 }
        records.forEach { record ->
            list[record.hour] += record.duration
        }
        _todayReport.value = list
    }

    fun getWeekReport() {
        val allRecords = allTaskRecords.value ?: return
        val list = LongArray(7) { 0 }
        val epoch = getCurrentDaySinceEpoch()
        val dayOfWeek = getCurrentDayOfWeek() - 1
        repeat(list.size) { index ->
            val day = epoch - (dayOfWeek - index - 1)
            val totalDuration = allRecords.filter { it.epochDay == day }.sumOf { it.duration }
            list[index] = totalDuration
        }
        _weekReport.value = list
    }
}