package com.timepad.timepadtracker.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.timepad.timepadtracker.data.TaskRecordDataSource
import com.timepad.timepadtracker.domain.TaskRecord
import com.timepad.timepadtracker.framework.db.TaskRecordEntity
import com.timepad.timepadtracker.framework.db.TimePadDatabase
import com.timepad.timepadtracker.framework.db.toDomainModel
import com.timepad.timepadtracker.framework.db.toRoomEntity

class RoomTaskRecordDataSource(database: TimePadDatabase) : TaskRecordDataSource {

    private val taskRecordDao = database.taskRecordDao

    override suspend fun add(taskRecord: TaskRecord) {
        taskRecordDao.add(taskRecord.toRoomEntity())
    }


    override suspend fun delete(taskRecord: TaskRecord) {
        taskRecordDao.delete(taskRecord.toRoomEntity())
    }

    override suspend fun update(taskRecord: TaskRecord) {
        taskRecordDao.update(taskRecord.toRoomEntity())
    }

    override fun getAll(): LiveData<List<TaskRecord>> {
        return taskRecordDao.getAll().map {
            convertToDomainModel(it)
        }
    }

    override fun getByDate(date: Long): LiveData<List<TaskRecord>> {
        return taskRecordDao.getByDay(date).map {
            convertToDomainModel(it)
        }
    }

    private fun convertToDomainModel(list: List<TaskRecordEntity>): List<TaskRecord> {
        return list.map { it.toDomainModel() }
    }
}