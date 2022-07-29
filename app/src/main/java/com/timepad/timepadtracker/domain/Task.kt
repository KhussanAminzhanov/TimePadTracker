package com.timepad.timepadtracker.domain

data class Task(
    val id: Int = 0,
    var iconId: Int = 0,
    var name: String = "",
    val tags: List<String> = emptyList(),
    var oneSessionTime: Long = 0,
    var totalTimeInMillis: Long = 0,
    val date: Long,
    var completedTaskTimes: List<Int>
)