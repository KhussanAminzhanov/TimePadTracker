package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskListRepository

class GetAllTaskLists(private val taskListRepository: TaskListRepository) {
    suspend operator fun invoke() = taskListRepository.getAll()
}