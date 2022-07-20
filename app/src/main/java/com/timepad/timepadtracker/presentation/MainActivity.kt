package com.timepad.timepadtracker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
    }
}