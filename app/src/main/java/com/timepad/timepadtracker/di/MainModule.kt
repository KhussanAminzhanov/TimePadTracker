package com.timepad.timepadtracker.di

import com.timepad.timepadtracker.data.TaskListRepository
import com.timepad.timepadtracker.data.TaskRecordRepository
import com.timepad.timepadtracker.data.TaskRepository
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.framework.RoomTaskDataSource
import com.timepad.timepadtracker.framework.RoomTaskListDataSource
import com.timepad.timepadtracker.framework.RoomTaskRecordDataSource
import com.timepad.timepadtracker.framework.db.TimePadDatabase
import com.timepad.timepadtracker.interactors.*
import com.timepad.timepadtracker.presentation.screens.report.ReportViewModel
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory { TimePadDatabase.getInstance(androidApplication()) }
    factory { TaskRepository(RoomTaskDataSource(get())) }
    factory { TaskRecordRepository(RoomTaskRecordDataSource(get())) }
    factory { TaskListRepository(RoomTaskListDataSource(get())) }

    factory {
        Interactions(
            addTask = AddTask(get()),
            deleteTask = DeleteTask(get()),
            getAllTasks = GetAllTasks(get()),
            updateTask = UpdateTask(get()),
            getByDate = GetByDate(get()),
            addTaskRecord = AddTaskRecord(get()),
            deleteTaskRecord = DeleteTaskRecord(get()),
            updateTaskRecord = UpdateTaskRecord(get()),
            getAllTaskRecords = GetAllTaskRecords(get()),
            getTaskRecordsByDay = GetTaskRecordsByDay(get()),
            addTaskList = AddTaskList(get()),
            deleteTaskList = DeleteTaskList(get()),
            updateTaskList = UpdateTaskList(get()),
            getAllTaskLists = GetAllTaskLists(get()),
            getList = GetList(get())
        )
    }

    viewModel { MainViewModel(get()) }
    viewModel { ReportViewModel(get()) }
}