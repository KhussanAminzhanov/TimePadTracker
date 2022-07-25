package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskRepository

class GetByDate(private val taskRepository: TaskRepository) {
    operator fun invoke(date: Long) = taskRepository.getByDate(date)
}