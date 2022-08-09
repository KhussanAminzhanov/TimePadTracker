package com.timepad.timepadtracker.framework.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface TaskListDao {
    @Insert(onConflict = IGNORE)
    fun add(list: TaskListEntity)

    @Delete
    fun delete(list: TaskListEntity)

    @Update
    fun update(list: TaskListEntity)

    @Query("SELECT * FROM task_list_table WHERE list_name = :name")
    fun getList(name: String): LiveData<TaskListEntity>

    @Query("SELECT * FROM task_list_table ORDER BY id")
    fun getAll(): LiveData<List<TaskListEntity>>
}