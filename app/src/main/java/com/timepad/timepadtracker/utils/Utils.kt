package com.timepad.timepadtracker.utils

import android.content.res.Resources
import java.time.LocalDate
import java.util.*

private val cal = Calendar.getInstance().apply { time = Date() }

fun getCurrentDayOfYear(): Int = cal.get(Calendar.DAY_OF_YEAR)

fun getCurrentHourOfDay(): Int = cal.get(Calendar.HOUR_OF_DAY)

fun getCurrentDayOfWeek(): Int = cal.get(Calendar.DAY_OF_WEEK)

fun getCurrentDaySinceEpoch(): Long = LocalDate.now().toEpochDay()

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}