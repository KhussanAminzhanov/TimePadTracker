package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskListRepository
import com.timepad.timepadtracker.domain.TaskList

class UpdateTaskList(private val taskListRepository: TaskListRepository) {
    suspend operator fun invoke(list: TaskList) = taskListRepository.updateList(list)
}