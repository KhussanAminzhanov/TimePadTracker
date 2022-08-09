package com.timepad.timepadtracker.presentation.navigation

sealed class MainScreens(val route: String) {
    object Main : MainScreens(route = "main_screen")
    object AllTasks : MainScreens(route = "all_tasks_screen")
    object Timer : MainScreens(route = "timer_screen")
    object NewTask : MainScreens(route = "new_task_screen")
    object Lists : MainScreens(route = "lists")
}
