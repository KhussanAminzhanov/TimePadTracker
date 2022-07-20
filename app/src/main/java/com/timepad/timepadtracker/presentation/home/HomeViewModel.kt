package com.timepad.timepadtracker.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.framework.Interactions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactions: Interactions,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData()

    fun loadTasks() = viewModelScope.launch(ioDispatcher) {
        _tasks.postValue(interactions.getAllTasks())
    }

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