package com.timepad.timepadtracker.interactors

import com.timepad.timepadtracker.data.TaskListRepository

class GetList(private val taskListRepository: TaskListRepository) {
    suspend operator fun invoke(name: String) = taskListRepository.getList(name)
}