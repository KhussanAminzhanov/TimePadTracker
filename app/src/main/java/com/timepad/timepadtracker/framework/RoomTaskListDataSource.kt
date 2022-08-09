package com.timepad.timepadtracker.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.timepad.timepadtracker.data.TaskListDataSource
import com.timepad.timepadtracker.domain.TaskList
import com.timepad.timepadtracker.framework.db.TimePadDatabase
import com.timepad.timepadtracker.framework.db.toDomainModel
import com.timepad.timepadtracker.framework.db.toRoomEntity

class RoomTaskListDataSource(database: TimePadDatabase) : TaskListDataSource {
    private val taskListDao = database.taskListDao

    override suspend fun add(list: TaskList) = taskListDao.add(list.toRoomEntity())

    override suspend fun delete(list: TaskList) = taskListDao.delete(list.toRoomEntity())

    override suspend fun update(list: TaskList) = taskListDao.update(list.toRoomEntity())

    override fun getAll(): LiveData<List<TaskList>> =
        Transformations.map(taskListDao.getAll()) { list ->
            list.map { it.toDomainModel() }
        }

    override fun getList(name: String) =
        Transformations.map(taskListDao.getList(name)) { it.toDomainModel() }
}