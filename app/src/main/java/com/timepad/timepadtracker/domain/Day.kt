package com.timepad.timepadtracker.domain

import java.util.*

data class Day(
    val day: Long = 0,
    private var totalTime: Long = 0,
    private val completedTaskTimes: MutableList<Int> = mutableListOf()
) {
    fun getTotalTime() = totalTime

    fun addCompletedTask(task: Task) {
        val cal = Calendar.getInstance().apply { time = Date() }
        val hour = cal.get(Calendar.HOUR)
        completedTaskTimes.add(hour)
        totalTime += task.oneSessionTime
    }

    fun getCompletedTaskTimes() = completedTaskTimes.toList()
}