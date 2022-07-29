package com.timepad.timepadtracker.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.utils.getCurrentDaySinceEpoch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate

class ReportViewModel(
    interactions: Interactions,
) : ViewModel() {

    private val _selectedTab = MutableLiveData<Int>()
    val selectedTab: LiveData<Int> = _selectedTab

    val tasks = interactions.getByDate(getCurrentDaySinceEpoch())
    val taskRecords = interactions.getTaskRecordsByDay(getCurrentDaySinceEpoch())

    fun setTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun getTodayReport() : List<Int> {
        return emptyList()
    }
}