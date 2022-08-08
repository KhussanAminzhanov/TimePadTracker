package com.timepad.timepadtracker.domain

import androidx.annotation.DrawableRes

data class Task(
    val id: Int = 0,
    val daySinceEpoch: Long,
    @DrawableRes var iconId: Int,
    var name: String,
    var category: String,
    var duration: Long = 1000,
    var totalTimeInMillis: Long = 0,
)