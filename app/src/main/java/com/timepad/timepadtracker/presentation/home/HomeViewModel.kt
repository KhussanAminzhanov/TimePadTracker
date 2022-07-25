package com.timepad.timepadtracker.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepad.timepadtracker.R
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
    val allTasks: LiveData<List<Task>> = interactions.getAllTasks()

    val categoriesOfTasks = listOf("Work", "Personal", "Sport", "Hobby", "Leisure Time")
    val tasksWithIcon =
        mapOf(categoriesOfTasks[0] to R.drawable.icon_monitor_circle, categoriesOfTasks[1] to R.drawable.icon_book_circle, categoriesOfTasks[2] to R.drawable.icon_barbell_circle, categoriesOfTasks[3] to R.drawable.icon_book_circle, categoriesOfTasks[4] to R.drawable.icon_code_circle)

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