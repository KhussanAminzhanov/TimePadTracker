package com.timepad.timepadtracker.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timepad.timepadtracker.domain.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "created_date") var date: Long,
    @ColumnInfo(name = "icon_id") var iconId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "one_session_time") val oneSessionTime: Long,
    @ColumnInfo(name = "total_time_in_millis") var totalTimeInMillis: Long,
)

fun Task.toRoomEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        date = this.daySinceEpoch,
        iconId = this.iconId,
        name = this.name,
        category = this.category,
        oneSessionTime = this.duration,
        totalTimeInMillis = this.totalTimeInMillis,
    )
}