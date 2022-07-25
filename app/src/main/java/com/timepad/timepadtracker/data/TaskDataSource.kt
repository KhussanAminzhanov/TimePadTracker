package com.timepad.timepadtracker.data

import androidx.lifecycle.LiveData
import com.timepad.timepadtracker.domain.Task

interface TaskDataSource {
    suspend fun add(task: Task)
    suspend fun delete(task: Task)
    suspend fun update(task: Task)
    fun getAll(): LiveData<List<Task>>
    fun getByDate(date: Long): LiveData<List<Task>>
}