package com.timepad.timepadtracker.presentation.screens.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.screens.home.HomeScreen
import com.timepad.timepadtracker.presentation.screens.report.ReportScreen
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onAddItemClick: () -> Unit,
    onTaskItemClick: (Task) -> Unit,
    onSeeAllClick: () -> Unit,
    onRightArrowClick: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                onAddItemClick = onAddItemClick
            )
        }
    ) { paddingValues ->
        BottomNavGraph(
            mainViewModel = mainViewModel,
            onTaskItemClick = onTaskItemClick,
            onSeeAllClick = onSeeAllClick,
            onRightArrowClick = onRightArrowClick,
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun BottomNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    onTaskItemClick: (Task) -> Unit,
    onSeeAllClick: () -> Unit,
    onRightArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        startDestination = Screens.route_home,
        navController = navController,
        modifier = modifier
    ) {
        composable(route = Screens.Home.route) {
            HomeScreen(
                mainViewModel = mainViewModel,
                onTaskItemClick = onTaskItemClick,
                onSeeAllClick = onSeeAllClick,
                onRightArrowClick = onRightArrowClick
            )
        }
        composable(route = Screens.Report.route) {
            ReportScreen(
                onBackArrowClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun BottomBar(
    onAddItemClick: () -> Unit,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        elevation = 0.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(vertical = 18.dp)
    ) {
        AddItem(
            screen = Screens.Home,
            currentDestination = currentDestination,
            navController = navController
        )
        BottomNavigationItem(
            selected = false,
            onClick = { onAddItemClick() },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.add_circle_rounded_48px),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(58.dp)
                )
            }
        )
        AddItem(
            screen = Screens.Report,
            currentDestination = currentDestination,
            navController = navController
        )
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val icon = if (selected) screen.selectedIcon else screen.unselectedIcon
    val tint = if (selected) Color.Black else Color(0xFF828282)
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
            navController.navigate(screen.route)
        },
        selected = selected,
        alwaysShowLabel = false
    )
}
