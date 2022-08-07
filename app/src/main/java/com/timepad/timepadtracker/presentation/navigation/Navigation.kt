package com.timepad.timepadtracker.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.timepad.timepadtracker.presentation.screens.main.MainScreen
import com.timepad.timepadtracker.presentation.screens.tasks.AllTasksScreen
import com.timepad.timepadtracker.presentation.screens.timer.TimerScreen
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = getViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                mainViewModel = mainViewModel,
                onAddItemClick = {},
                onTaskItemClick = { task ->
                    mainViewModel.setSelectedTask(task)
                    navController.navigate(Screen.Timer.route)
                },
                onSeeAllClick = {
                    navController.navigate(Screen.AllTasks.route)
                },
                onRightArrowClick = {
                    navController.navigate(Screen.Timer.route)
                }
            )
        }
        composable(route = Screen.AllTasks.route) {
            AllTasksScreen(
                mainViewModel = mainViewModel,
                onTaskItemClick = {},
                onBackArrowClick = {}
            )
        }
        composable(route = Screen.Timer.route) {
            TimerScreen()
        }
    }
}