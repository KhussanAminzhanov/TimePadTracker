package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRepository

class GetTask(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskId: Int) = taskRepository.get(taskId)
}