package com.timepad.timepadtracker.framework

import com.timepad.timepadtracker.interactors.*

data class Interactions(
    val addTask: AddTask,
    val deleteTask: DeleteTask,
    val getAllTasks: GetAllTasks,
    val updateTask: UpdateTask,
    val getByDate: GetByDate,
    val addTaskRecord: AddTaskRecord,
    val deleteTaskRecord: DeleteTaskRecord,
    val updateTaskRecord: UpdateTaskRecord,
    val getAllTaskRecords: GetAllTaskRecords,
    val getTaskRecordsByDay: GetTaskRecordsByDay
)