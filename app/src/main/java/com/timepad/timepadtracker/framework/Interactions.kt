package com.timepad.timepadtracker.framework

import com.timepad.timepadtracker.interactors.*

data class Interactions(
    val addTask: AddTask,
    val deleteTask: DeleteTask,
    val getAllTasks: GetAllTasks,
    val updateTask: UpdateTask,
    val getByDate: GetByDate
)