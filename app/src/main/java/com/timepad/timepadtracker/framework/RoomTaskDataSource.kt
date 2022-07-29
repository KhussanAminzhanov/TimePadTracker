package com.timepad.timepadtracker.framework

import androidx.lifecycle.Transformations
import com.timepad.timepadtracker.data.TaskDataSource
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.framework.db.TaskEntity
import com.timepad.timepadtracker.framework.db.TimePadDatabase
import com.timepad.timepadtracker.framework.db.toRoomEntity

class RoomTaskDataSource(database: TimePadDatabase) : TaskDataSource {

    private val taskDao = database.taskDao

    override suspend fun add(task: Task) {
        taskDao.add(task.toRoomEntity())
    }

    override suspend fun delete(task: Task) {
        taskDao.delete(task.toRoomEntity())
    }

    override suspend fun update(task: Task) {
        taskDao.update(task.toRoomEntity())
    }

    override fun getAll() = Transformations.map(taskDao.getAll()) {
        convertToDomainModel(it)
    }

    override fun getByDate(date: Long) = Transformations.map(taskDao.getByDate(date)) {
        convertToDomainModel(it)
    }

    private fun convertToDomainModel(list: List<TaskEntity>): List<Task> {
        return list.map { taskEntity ->
            Task(
                id = taskEntity.id,
                name = taskEntity.name,
                iconId = taskEntity.iconId,
                daySinceEpoch = taskEntity.date,
                category = taskEntity.category,
                duration = taskEntity.oneSessionTime,
                totalTimeInMillis = taskEntity.totalTimeInMillis
            )
        }
    }
}