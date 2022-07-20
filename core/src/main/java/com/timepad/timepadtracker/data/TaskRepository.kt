package com.timepad.timepadtracker.data

import com.timepad.timepadtracker.domain.Task

class TaskRepository(private val dataSource: TaskDataSource) {

    suspend fun addTask(task: Task) = dataSource.add(task)
    suspend fun deleteTask(task: Task) = dataSource.delete(task)
    suspend fun updateTask(task: Task) = dataSource.update(task)
    suspend fun get(taskId: Int) = dataSource.get(taskId)
    suspend fun getAll() = dataSource.getAll()

}