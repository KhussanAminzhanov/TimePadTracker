package com.timepad.timepadtracker.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentMainBinding
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.home.HomeViewModel
import com.timepad.timepadtracker.utils.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val homeViewModel: HomeViewModel by viewModel()
    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupNavigation()
        setupBottomNav()
    }

    private fun setupNavigation() {
        navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        binding.bottomNavMain.setupWithNavController(navController)
        binding.toolbarMain.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupBottomNav() {
        val menuView = binding.bottomNavMain.menuView as BottomNavigationMenuView
        val menuItem = menuView.findItemView(R.id.item_add_task) as BottomNavigationItemView
        menuItem.setIconSize(44.dp)
        menuItem.setOnClickListener {
            val tags = listOf("Work", "Study")
            homeViewModel.addTask(Task(0, 0, "Task ${counter++}", tags, 0, Date()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}