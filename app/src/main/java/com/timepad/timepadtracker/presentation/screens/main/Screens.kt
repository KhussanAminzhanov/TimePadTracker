package com.timepad.timepadtracker.presentation.screens.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.timepad.timepadtracker.R


sealed class Screens(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int
) {

    companion object {
        val screens = listOf(
            Home,
            Report
        )
        const val route_home = "home"
        const val route_report = "report"
    }

    object Home : Screens(
        route = route_home,
        label = R.string.home_label,
        unselectedIcon = R.drawable.ic_time_outline,
        selectedIcon = R.drawable.ic_time_filled
    )

    object Report : Screens(
        route = route_report,
        label = R.string.report_label,
        unselectedIcon = R.drawable.ic_pie_chart_outline,
        selectedIcon = R.drawable.ic_pie_chart_filled
    )
}