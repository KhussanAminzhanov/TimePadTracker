package com.timepad.timepadtracker.utils

import java.util.*

fun getCurrentDay(): Long {
    val cal = Calendar.getInstance().apply { time = Date() }
    return cal.get(Calendar.DAY_OF_YEAR).toLong()
}