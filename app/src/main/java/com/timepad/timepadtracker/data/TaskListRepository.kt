package com.timepad.timepadtracker.data

import com.timepad.timepadtracker.domain.TaskList
import com.timepad.timepadtracker.presentation.screens.report.data

class TaskListRepository(private val dataSource: TaskListDataSource) {
    suspend fun addTaskList(list: TaskList) = dataSource.add(list)
    suspend fun deleteTaskList(list: TaskList) = dataSource.delete(list)
    suspend fun updateList(list: TaskList) = dataSource.update(list)
    fun getAll() = dataSource.getAll()
    fun getList(name: String) = dataSource.getList(name)
}