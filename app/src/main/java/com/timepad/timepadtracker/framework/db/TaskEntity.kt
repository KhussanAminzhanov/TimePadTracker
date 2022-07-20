package com.timepad.timepadtracker.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timepad.timepadtracker.domain.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "icon_id") var iconId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "tags") var tags: List<String>,
    @ColumnInfo(name = "total_time_in_millis") var totalTimeInMillis: Long
)

fun Task.toRoomEntity() : TaskEntity {
    return TaskEntity(
        iconId = this.iconId,
        name = this.name,
        tags = this.tags,
        totalTimeInMillis = this.totalTimeInMillis
    )
}