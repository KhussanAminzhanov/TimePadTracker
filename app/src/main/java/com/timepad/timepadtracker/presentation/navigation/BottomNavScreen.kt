package com.timepad.timepadtracker.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.timepad.timepadtracker.R


sealed class BottomNavScreen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int
) {
    object Home : BottomNavScreen(
        route = "home_screen",
        label = R.string.home_label,
        unselectedIcon = R.drawable.ic_time_outline,
        selectedIcon = R.drawable.ic_time_filled
    )

    object Report : BottomNavScreen(
        route = "report_screen",
        label = R.string.report_label,
        unselectedIcon = R.drawable.ic_pie_chart_outline,
        selectedIcon = R.drawable.ic_pie_chart_filled
    )
}