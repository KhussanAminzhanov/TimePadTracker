package com.timepad.timepadtracker.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.databinding.FragmentMainBinding
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.composeViewMain.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                TimePadTheme {
                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        MainScreen()
                    }
                }
            }
        }
    }

//        menuItem.setOnClickListener {
//            newTaskBottomSheet.show(childFragmentManager, null)
//        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}