package com.timepad.timepadtracker.presentation.screens.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.timepad.timepadtracker.presentation.screens.home.HomeScreen
import com.timepad.timepadtracker.presentation.screens.report.ReportScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        BottomNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        startDestination = MainScreens.route_home,
        navController = navController,
        modifier = modifier
    ) {
        composable(route = MainScreens.Home.route) {
            HomeScreen(
                onTaskItemClick = {},
                onSeeAllClick = {},
                onRightArrowClick = {}
            )
        }
        composable(route = MainScreens.Report.route) {
            ReportScreen(
                onBackArrowClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun BottomBar(
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
            screen = MainScreens.Home,
            currentDestination = currentDestination,
            navController = navController
        )
        BottomNavigationItem(
            selected = false,
            onClick = { /*TODO*/ },
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
            screen = MainScreens.Report,
            currentDestination = currentDestination,
            navController = navController
        )
    }
}

@Composable
fun RowScope.AddItem(
    screen: MainScreens,
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