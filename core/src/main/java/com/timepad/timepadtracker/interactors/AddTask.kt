package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRepository
import com.timepad.timepadtracker.domain.Task

class AddTask(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) = taskRepository.addTask(task)
}