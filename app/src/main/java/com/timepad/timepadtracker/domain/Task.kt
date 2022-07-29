package com.timepad.timepadtracker.domain

data class Task(
    val id: Int = 0,
    val createdDate: Long,
    var iconId: Int,
    var name: String,
    var category: String,
    var oneSessionTime: Long = 1000,
    var totalTimeInMillis: Long = 0,
)