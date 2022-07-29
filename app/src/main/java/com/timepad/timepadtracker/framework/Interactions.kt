package com.timepad.timepadtracker.framework

import com.timepad.timepadtracker.interactors.*

data class Interactions(
    val addTask: AddTask,
    val deleteTask: DeleteTask,
    val getAllTasks: GetAllTasks,
    val updateTask: UpdateTask,
    val getByDate: GetByDate,
    val addDay: AddDay,
    val deleteDay: DeleteDay,
    val updateDay: UpdateDay,
    val getAllDays: GetAllDays,
    val getDay: GetDay
)