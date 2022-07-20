package com.timepad.timepadtracker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.utils.dp
import com.timepad.timepadtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setupNavigation()
        setupBottomNav()
    }

    private fun setupNavigation() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container_main) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        binding.toolbarMain.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavMain.setupWithNavController(navController)
    }

    private fun setupBottomNav() {
        val menuView = binding.bottomNavMain.menuView as BottomNavigationMenuView
        val menuItem = menuView.findItemView(R.id.item_add_task) as BottomNavigationItemView
        menuItem.setIconSize(44.dp)
        menuItem.setOnClickListener {
            Toast.makeText(this, "Add task button clicked", Toast.LENGTH_SHORT).show()
        }
    }
}