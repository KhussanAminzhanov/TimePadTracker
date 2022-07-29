package com.timepad.timepadtracker.framework.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTagsString(tags: String): List<String> {
        return tags.split("|")
    }

    @TypeConverter
    fun tagsToString(tags: List<String>): String {
        return tags.joinToString(separator = "|", prefix = "", postfix = "")
    }

    @TypeConverter
    fun fromCompletedTaskTimesString(times: String): List<Int> {
        return times.split("|").map { it.toInt() }
    }

    @TypeConverter
    fun timesToString(times: List<Int>): String {
        return times.joinToString(separator = "|", prefix = "", postfix = "")
    }
}