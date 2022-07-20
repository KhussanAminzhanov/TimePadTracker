package com.timepad.timepadtracker.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TimePadDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

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