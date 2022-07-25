package com.timepad.timepadtracker.domain

import java.util.*

data class Task(
    val id: Int = 0,
    val iconId: Int = 0,
    val name: String = "",
    val tags: List<String> = emptyList(),
    val totalTimeInMillis: Long = 0,
    val date: Date
)