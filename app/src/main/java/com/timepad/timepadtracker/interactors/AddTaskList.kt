package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskListRepository
import com.timepad.timepadtracker.domain.TaskList

class AddTaskList(private val taskListRepository: TaskListRepository) {
    suspend operator fun invoke(list: TaskList) = taskListRepository.addTaskList(list)
}