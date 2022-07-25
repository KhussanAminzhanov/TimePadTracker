package com.timepad.timepadtracker.framework

import androidx.lifecycle.Transformations
import com.timepad.timepadtracker.data.TaskDataSource
import com.timepad.timepadtracker.domain.Task
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
        it.map { taskEntity ->
            Task(
                id = taskEntity.id,
                iconId = taskEntity.iconId,
                name = taskEntity.name,
                tags = taskEntity.tags,
                totalTimeInMillis = taskEntity.totalTimeInMillis,
                date = taskEntity.date
            )
        }
    }
}