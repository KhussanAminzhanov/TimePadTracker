package com.timepad.timepadtracker.data

import com.timepad.timepadtracker.domain.TaskRecord

class TaskRecordRepository(private val dataSource: TaskRecordDataSource) {
    suspend fun addTaskRecord(taskRecord: TaskRecord) = dataSource.add(taskRecord)
    suspend fun deleteTaskRecord(taskRecord: TaskRecord) = dataSource.delete(taskRecord)
    suspend fun updateTaskRecord(taskRecord: TaskRecord) = dataSource.update(taskRecord)
    fun getAll() = dataSource.getAll()
    fun getByDate(date: Long) = dataSource.getByDate(date)
}