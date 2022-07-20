package com.timepad.timepadtracker

import android.app.Application
import com.timepad.timepadtracker.data.TaskRepository
import com.timepad.timepadtracker.di.appModule
import com.timepad.timepadtracker.di.mainModule
import com.timepad.timepadtracker.framework.RoomTaskDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, mainModule)
        }
    }
}