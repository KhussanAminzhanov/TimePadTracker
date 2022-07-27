package com.timepad.timepadtracker.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timepad.timepadtracker.framework.Interactions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate

class ReportViewModel(
    private val interactions: Interactions,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _selectedTab = MutableLiveData<Int>()
    val selectedTab: LiveData<Int> = _selectedTab

    val tasks = interactions.getByDate(LocalDate.now().toEpochDay())

    fun setTab(tab: Int) {
        _selectedTab.value = tab
    }
}