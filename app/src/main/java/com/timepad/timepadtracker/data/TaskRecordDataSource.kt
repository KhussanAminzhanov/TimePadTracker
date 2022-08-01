package com.timepad.timepadtracker.data

import androidx.lifecycle.LiveData
import com.timepad.timepadtracker.domain.TaskRecord

interface TaskRecordDataSource {
    suspend fun add(taskRecord: TaskRecord)
    suspend fun delete(taskRecord: TaskRecord)
    suspend fun update(taskRecord: TaskRecord)
    fun getAll(): LiveData<List<TaskRecord>>
    fun getByDate(date: Long): LiveData<List<TaskRecord>>
}