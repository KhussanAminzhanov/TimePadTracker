package com.timepad.timepadtracker.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.timepad.timepadtracker.R

fun Fragment.findTopNavController(): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_activity_main) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}