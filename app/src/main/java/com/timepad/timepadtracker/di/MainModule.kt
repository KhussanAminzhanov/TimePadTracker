package com.timepad.timepadtracker.di

import com.timepad.timepadtracker.data.TaskRepository
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.framework.RoomTaskDataSource
import com.timepad.timepadtracker.framework.db.TimePadDatabase
import com.timepad.timepadtracker.interactors.*
import com.timepad.timepadtracker.presentation.MainViewModel
import com.timepad.timepadtracker.presentation.home.HomeViewModel
import com.timepad.timepadtracker.presentation.report.ReportViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory { TimePadDatabase.getInstance(androidApplication()) }
    factory { TaskRepository(RoomTaskDataSource(get())) }
    factory {
        Interactions(
            AddTask(get()),
            DeleteTask(get()),
            GetAllTasks(get()),
            UpdateTask(get()),
            GetByDate(get())
        )
    }

    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), Dispatchers.IO) }
    viewModel { ReportViewModel() }
}