package com.timepad.timepadtracker.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.framework.Interactions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val interactions: Interactions,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val tasks: LiveData<List<Task>> = interactions.getByDate(LocalDate.now().toEpochDay())

    fun addTask(task: Task) = viewModelScope.launch(ioDispatcher) {
        interactions.addTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch(ioDispatcher) {
        interactions.deleteTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch(ioDispatcher) {
        interactions.updateTask(task)
    }
}