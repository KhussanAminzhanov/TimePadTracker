package com.timepad.timepadtracker.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.timepad.timepadtracker.presentation.navigation.Navigation
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimePadTheme {
                Navigation()
            }
        }
    }
}