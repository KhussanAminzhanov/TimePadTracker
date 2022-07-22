package com.timepad.timepadtracker.framework.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE)
    fun add(task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)

    @Update
    fun update(task: TaskEntity)

    @Query("SELECT * FROM task_table ORDER BY id")
    fun getAll(): LiveData<List<TaskEntity>>
}