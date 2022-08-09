package com.timepad.timepadtracker.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TaskEntity::class, TaskRecordEntity::class, TaskListEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TimePadDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val taskRecordDao: TaskRecordDao
    abstract val taskListDao: TaskListDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: TimePadDatabase

        fun getInstance(context: Context): TimePadDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TimePadDatabase::class.java,
                        "time_pad_database"
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}