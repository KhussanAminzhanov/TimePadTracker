package com.timepad.timepadtracker.data

import androidx.lifecycle.LiveData
import com.timepad.timepadtracker.domain.TaskList

interface TaskListDataSource {
    suspend fun add(list: TaskList)
    suspend fun delete(list: TaskList)
    suspend fun update(list: TaskList)
    fun getAll(): LiveData<List<TaskList>>
    fun getList(name: String): LiveData<TaskList>
}