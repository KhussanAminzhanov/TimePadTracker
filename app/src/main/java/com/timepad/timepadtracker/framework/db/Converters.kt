package com.timepad.timepadtracker.framework.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTagsString(tags: String): List<String> {
        return tags.split("|")
    }

    @TypeConverter
    fun stringToList(tags: List<String>): String {
        return tags.joinToString(separator = "|", prefix = "", postfix = "")
    }
}