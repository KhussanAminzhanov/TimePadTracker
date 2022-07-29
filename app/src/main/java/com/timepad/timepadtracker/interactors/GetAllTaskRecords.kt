package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRecordRepository

class GetAllTaskRecords(private val taskRecordRepository: TaskRecordRepository) {
    suspend operator fun invoke() = taskRecordRepository.getAll()
}