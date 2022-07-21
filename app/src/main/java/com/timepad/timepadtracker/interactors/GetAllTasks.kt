package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRepository

class GetAllTasks(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAll()
}