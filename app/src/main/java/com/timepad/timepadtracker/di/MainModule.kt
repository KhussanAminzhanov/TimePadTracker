package com.timepad.timepadtracker.di

import com.timepad.timepadtracker.data.DayRepository
import com.timepad.timepadtracker.data.TaskRepository
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.framework.RoomDayDataSource
import com.timepad.timepadtracker.framework.RoomTaskDataSource
import com.timepad.timepadtracker.framework.db.TimePadDatabase
import com.timepad.timepadtracker.interactors.*
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.presentation.report.ReportViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory { TimePadDatabase.getInstance(androidApplication()) }
    factory { TaskRepository(RoomTaskDataSource(get())) }
    factory { DayRepository(RoomDayDataSource(get())) }
    factory {
        Interactions(
            addTask = AddTask(get()),
            deleteTask = DeleteTask(get()),
            getAllTasks = GetAllTasks(get()),
            updateTask = UpdateTask(get()),
            getByDate = GetByDate(get()),
            addDay = AddDay(get()),
            deleteDay = DeleteDay(get()),
            updateDay = UpdateDay(get()),
            getAllDays = GetAllDays(get()),
            getDay = GetDay(get())
        )
    }

    viewModel { MainViewModel(get()) }
    viewModel { ReportViewModel(get()) }
}