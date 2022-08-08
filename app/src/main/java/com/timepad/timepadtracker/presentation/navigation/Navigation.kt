package com.timepad.timepadtracker.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.timepad.timepadtracker.presentation.screens.main.MainScreen
import com.timepad.timepadtracker.presentation.screens.newtask.NewTaskScreen
import com.timepad.timepadtracker.presentation.screens.tasks.TasksScreen
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
                mainNavController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable(route = Screen.AllTasks.route) {
            TasksScreen(
                mainNavController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable(route = Screen.Timer.route) {
            TimerScreen(
                mainNavController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable(route = Screen.NewTask.route) {
            NewTaskScreen(
                mainNavController = navController,
                mainViewModel = mainViewModel
            )
        }
    }
}