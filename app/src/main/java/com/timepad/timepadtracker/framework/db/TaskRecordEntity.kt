package com.timepad.timepadtracker.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timepad.timepadtracker.domain.TaskRecord

@Entity(tableName = "task_record_table")
data class TaskRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "day_since_epoch") val daySinceEpoch: Long,
    @ColumnInfo(name = "created_at_hour") val hour: Int,
    @ColumnInfo(name = "duration") val duration: Long
)

fun TaskRecord.toRoomEntity(): TaskRecordEntity {
    return TaskRecordEntity(
        id = this.id,
        daySinceEpoch = this.epochDay,
        hour = this.hour,
        duration = this.duration
    )
}

fun TaskRecordEntity.toDomainModel(): TaskRecord {
    return TaskRecord(
        id = this.id,
        epochDay = this.daySinceEpoch,
        hour = this.hour,
        duration = this.duration
    )
}