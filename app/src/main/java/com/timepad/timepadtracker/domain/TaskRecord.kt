package com.timepad.timepadtracker.domain

data class TaskRecord(
    val id: Int = 0,
    val epochDay: Long,
    val hour: Int,
    val duration: Long
)