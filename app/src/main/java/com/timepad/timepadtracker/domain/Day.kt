package com.timepad.timepadtracker.domain

data class Day(
    val id: Int = 0,
    var tasksCompleted: Int = 0,
    var timeDuration: Long = 0,
)