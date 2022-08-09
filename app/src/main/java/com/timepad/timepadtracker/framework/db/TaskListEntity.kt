package com.timepad.timepadtracker.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timepad.timepadtracker.domain.TaskList

@Entity(tableName = "task_list_table")
data class TaskListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "list_name") var name: String,
    @ColumnInfo(name = "list_icon_res") var iconRes: Int
)

fun TaskList.toRoomEntity() = TaskListEntity(
    id = this.id,
    name = this.name,
    iconRes = this.iconRes
)

fun TaskListEntity.toDomainModel() = TaskList(
    id = this.id,
    name = this.name,
    iconRes = this.iconRes
)