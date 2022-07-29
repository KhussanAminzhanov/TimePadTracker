package com.timepad.timepadtracker.data

import androidx.lifecycle.LiveData
import com.timepad.timepadtracker.domain.Day

interface DayDataSource {
    suspend fun add(day: Day)
    suspend fun delete(day: Day)
    suspend fun update(day: Day)
    fun getAll(): LiveData<List<Day>>
    fun getDay(day: Int): LiveData<Day>
}