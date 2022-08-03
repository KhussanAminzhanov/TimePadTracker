package com.timepad.timepadtracker.presentation.screens.main

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentMainBinding
import com.timepad.timepadtracker.utils.dp

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private val newTaskBottomSheet: NewTaskBottomSheet = NewTaskBottomSheet()

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
        binding.bottomNavMain.setupWithNavController(navController)
    }

    @SuppressLint("RestrictedApi")
    private fun setupBottomNav() {
        val menuView = binding.bottomNavMain.menuView as BottomNavigationMenuView
        val menuItem = menuView.findItemView(R.id.item_add_task) as BottomNavigationItemView
        menuItem.setIconTintList(ColorStateList.valueOf(Color.BLACK))
        menuItem.setIconSize((52.dp))
        menuItem.setOnClickListener {
            newTaskBottomSheet.show(childFragmentManager, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}