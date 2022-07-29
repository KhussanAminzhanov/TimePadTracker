package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRecordRepository
import com.timepad.timepadtracker.domain.TaskRecord

class AddTaskRecord(private val taskRecordRepository: TaskRecordRepository) {
    suspend operator fun invoke(taskRecord: TaskRecord) =
        taskRecordRepository.addTaskRecord(taskRecord)
}