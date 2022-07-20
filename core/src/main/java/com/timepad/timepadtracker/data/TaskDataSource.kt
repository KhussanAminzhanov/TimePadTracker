package com.timepad.timepadtracker.data

import com.timepad.timepadtracker.domain.Task

interface TaskDataSource {
    suspend fun add(task: Task)
    suspend fun delete(task: Task)
    suspend fun update(task: Task)
    suspend fun get(taskId: Int): Task
    suspend fun getAll(): List<Task>
}