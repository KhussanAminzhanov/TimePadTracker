package com.timepad.timepadtracker.utils

import java.util.concurrent.TimeUnit

fun Long.formatTimeMillis(str: String): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes =
        TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(hours)
    val seconds =
        TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(
            minutes
        )
    return String.format(str, hours, minutes, seconds)
}