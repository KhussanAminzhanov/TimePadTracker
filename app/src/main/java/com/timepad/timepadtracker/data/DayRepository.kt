package com.timepad.timepadtracker.data

import com.timepad.timepadtracker.domain.Day

class DayRepository(private val dataSource: DayDataSource) {
    suspend fun addDay(day: Day) = dataSource.add(day)
    suspend fun deleteDay(day: Day) = dataSource.delete(day)
    suspend fun update(day: Day) = dataSource.update(day)
    fun getAll() = dataSource.getAll()
    fun getDay(day: Int) = dataSource.getDay(day)
}