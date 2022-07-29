package com.timepad.timepadtracker.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timepad.timepadtracker.domain.Day

@Entity(tableName = "day_table")
data class DayEntity(
    @PrimaryKey(autoGenerate = false) val day: Long,
    @ColumnInfo(name = "total_time") var totalTime: Long,
    @ColumnInfo(name = "completed_task_times") var completedTaskTimes: List<Int>
)

fun Day.toRoomEntity(): DayEntity {
    return DayEntity(
        day = this.day,
        totalTime = this.getTotalTime(),
        completedTaskTimes = this.getCompletedTaskTimes()
    )
}

fun DayEntity.toDomainModel(): Day {
    return Day(
        day = this.day,
        totalTime = this.totalTime,
        completedTaskTimes = this.completedTaskTimes.toMutableList()
    )
}