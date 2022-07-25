package com.timepad.timepadtracker.framework.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTagsString(tags: String): List<String> {
        return tags.split("|")
    }

    @TypeConverter
    fun stringToList(tags: List<String>): String {
        return tags.joinToString(separator = "|", prefix = "", postfix = "")
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }
}