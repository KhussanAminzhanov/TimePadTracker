package com.timepad.timepadtracker.domain

data class Task(
    val id: Int = 0,
    val iconId: Int = 0,
    val name: String = "",
    val tags: List<String> = emptyList(),
    val totalTimeInMillis: Long = 0,
)