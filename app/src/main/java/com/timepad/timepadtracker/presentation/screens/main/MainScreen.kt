package com.timepad.timepadtracker.presentation.screens.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.presentation.navigation.BottomNavScreen
import com.timepad.timepadtracker.presentation.navigation.MainScreens
import com.timepad.timepadtracker.presentation.screens.home.HomeScreen
import com.timepad.timepadtracker.presentation.screens.report.ReportScreen
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel

@Composable
fun MainScreen(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(
                mainNavController = mainNavController,
                navController = navController,
            )
        }
    ) { paddingValues ->
        BottomNavGraph(
            mainNavController = mainNavController,
            navController = navController,
            mainViewModel = mainViewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun BottomNavGraph(
    mainNavController: NavHostController,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        startDestination = BottomNavScreen.Home.route,
        navController = navController,
        modifier = modifier
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen(
                mainNavController = mainNavController,
                mainViewModel = mainViewModel
            )
        }
        composable(route = BottomNavScreen.Report.route) {
            ReportScreen(
                onBackArrowClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun BottomBar(
    mainNavController: NavHostController,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .padding(vertical = 18.dp)
    ) {
        AddItem(
            screen = BottomNavScreen.Home,
            currentDestination = currentDestination,
            navController = navController
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                mainNavController.navigate(MainScreens.NewTask.route) { launchSingleTop = true }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.add_circle_rounded_48px),
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(58.dp)
                )
            }
        )
        AddItem(
            screen = BottomNavScreen.Report,
            currentDestination = currentDestination,
            navController = navController
        )
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val icon = if (selected) screen.selectedIcon else screen.unselectedIcon
    val tint = if (selected) MaterialTheme.colors.secondary else Color(0xFF828282)
    BottomNavigationItem(
        icon = {
            Icon(
                tint = tint,
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        },
        onClick = {
            if (!selected) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        },
        selected = selected,
        alwaysShowLabel = false
    )
}
