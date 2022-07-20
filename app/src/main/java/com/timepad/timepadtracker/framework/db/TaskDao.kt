package com.timepad.timepadtracker.framework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE)
    suspend fun add(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    fun get(taskId: Int) : TaskEntity

    @Query("SELECT * FROM task_table ORDER BY id")
    fun getAll() : List<TaskEntity>
}