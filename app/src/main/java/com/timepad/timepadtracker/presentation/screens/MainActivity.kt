package com.timepad.timepadtracker.presentation.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import com.timepad.timepadtracker.presentation.navigation.Navigation
import com.timepad.timepadtracker.presentation.general.NoRippleTheme
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimePadTheme {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Navigation()
                }
            }
        }
    }
}