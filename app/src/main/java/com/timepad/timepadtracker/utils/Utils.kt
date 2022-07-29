package com.timepad.timepadtracker.utils

import java.time.LocalDate
import java.util.*

fun getCurrentDayOfYear(): Int {
    val cal = Calendar.getInstance().apply { time = Date() }
    return cal.get(Calendar.DAY_OF_YEAR)
}

fun getCurrentHourOfDay(): Int {
    val cal = Calendar.getInstance().apply { time = Date() }
    return cal.get(Calendar.HOUR_OF_DAY)
}

fun getCurrentDaySinceEpoch(): Long {
    return LocalDate.now().toEpochDay()
}