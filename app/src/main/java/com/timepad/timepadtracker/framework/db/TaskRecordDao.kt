package com.timepad.timepadtracker.framework.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TaskRecordDao {
    @Insert(onConflict = REPLACE)
    fun add(taskRecordEntity: TaskRecordEntity)

    @Delete
    fun delete(taskRecordEntity: TaskRecordEntity)

    @Update
    fun update(task: TaskRecordEntity)

    @Query("SELECT * FROM task_record_table ORDER BY day_since_epoch")
    fun getAll(): LiveData<List<TaskRecordEntity>>

    @Query("SELECT * FROM task_record_table WHERE day_since_epoch = :daySinceEpoch")
    fun getByDay(daySinceEpoch: Long): LiveData<List<TaskRecordEntity>>
}