package com.timepad.timepadtracker.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.timepad.timepadtracker.data.DayDataSource
import com.timepad.timepadtracker.domain.Day
import com.timepad.timepadtracker.framework.db.TimePadDatabase
import com.timepad.timepadtracker.framework.db.toDomainModel
import com.timepad.timepadtracker.framework.db.toRoomEntity

class RoomDayDataSource(database: TimePadDatabase) : DayDataSource {

    private val dayDao = database.dayDao

    override suspend fun add(day: Day) = dayDao.add(day.toRoomEntity())

    override suspend fun delete(day: Day) = dayDao.delete(day.toRoomEntity())

    override suspend fun update(day: Day) = dayDao.update(day.toRoomEntity())

    override fun getAll(): LiveData<List<Day>> = dayDao.getAll().map {
        it.map { dayEntity -> dayEntity.toDomainModel() }
    }

    override fun getDay(day: Int): LiveData<Day> = dayDao.getDay(day).map { it.toDomainModel() }
}