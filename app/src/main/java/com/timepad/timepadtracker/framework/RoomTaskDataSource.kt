package com.timepad.timepadtracker.framework

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

    override suspend fun get(taskId: Int): Task {
        val task = taskDao.get(taskId)
        return Task(
            iconId = task.iconId,
            name = task.name,
            tags = task.tags,
            totalTimeInMillis = task.totalTimeInMillis
        )
    }

    override suspend fun getAll(): List<Task> {
        return taskDao.getAll().map {
            Task(
                iconId = it.iconId,
                name = it.name,
                tags = it.tags,
                totalTimeInMillis = it.totalTimeInMillis
            )
        }
    }
}