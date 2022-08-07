package com.timepad.timepadtracker.presentation.navigation

sealed class Screen(val route: String) {
    object Main : Screen(route = "main_screen")
    object AllTasks : Screen(route = "all_tasks_screen")
    object Timer : Screen(route = "timer_screen")
}
