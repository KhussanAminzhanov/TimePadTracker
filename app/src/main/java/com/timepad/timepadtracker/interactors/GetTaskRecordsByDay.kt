package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRecordRepository

class GetTaskRecordsByDay(private val taskRecordRepository: TaskRecordRepository) {
    operator fun invoke(daySinceEpoch: Long) = taskRecordRepository.getByDate(daySinceEpoch)
}