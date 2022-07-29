package com.timepad.timepadtracker.framework.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface DayDao {
    @Insert(onConflict = REPLACE)
    fun add(day: DayEntity)

    @Delete
    fun delete(day: DayEntity)

    @Update
    fun update(day: DayEntity)

    @Query("SELECT * FROM day_table ORDER BY day")
    fun getAll(): LiveData<List<DayEntity>>

    @Query("SELECT * FROM day_table WHERE day = :day")
    fun getDay(day: Int): LiveData<DayEntity>
}