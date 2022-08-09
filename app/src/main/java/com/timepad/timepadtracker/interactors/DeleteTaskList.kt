package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskListRepository
import com.timepad.timepadtracker.domain.TaskList

class DeleteTaskList(private val taskListRepository: TaskListRepository) {
    suspend operator fun invoke(list: TaskList) = taskListRepository.deleteTaskList(list)
}