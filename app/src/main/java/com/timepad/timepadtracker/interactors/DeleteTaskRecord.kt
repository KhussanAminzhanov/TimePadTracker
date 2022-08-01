package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRecordRepository
import com.timepad.timepadtracker.domain.TaskRecord

class DeleteTaskRecord(private val taskRecordRepository: TaskRecordRepository) {
    suspend operator fun invoke(taskRecord: TaskRecord) =
        taskRecordRepository.deleteTaskRecord(taskRecord)
}