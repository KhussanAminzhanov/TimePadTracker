package com.timepad.timepadtracker.presentation.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.utils.getCurrentDayOfWeek
import com.timepad.timepadtracker.utils.getCurrentDaySinceEpoch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ReportViewModel(
    private val interactions: Interactions,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _selectedTab = MutableLiveData<Int>()
    val selectedTab: LiveData<Int> = _selectedTab

    val taskRecords = interactions.getTaskRecordsByDay(getCurrentDaySinceEpoch())

    private val _todayReport = MutableLiveData<LongArray>()
    val todayReport: LiveData<LongArray> = _todayReport

    private val _weekReport = MutableLiveData<LongArray>()
    val weekReport: LiveData<LongArray> = _weekReport

    init {
        getTodayReport()
        getWeekReport()
    }

    fun setTab(tab: Int) {
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

    }

    companion object {
        private const val TAG = "ReportViewModel"
    }
}