package com.timepad.timepadtracker.domain

data class Task(
    val id: Int = 0,
    val daySinceEpoch: Long,
    var iconId: Int,
    var name: String,
    var category: String,
    var duration: Long = 1000,
    var totalTimeInMillis: Long = 0,
)