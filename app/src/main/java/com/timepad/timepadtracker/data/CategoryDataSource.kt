package com.timepad.timepadtracker.data

import androidx.compose.ui.graphics.Color
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.presentation.theme.Green
import com.timepad.timepadtracker.presentation.theme.Orange
import com.timepad.timepadtracker.presentation.theme.Purple
import com.timepad.timepadtracker.presentation.theme.Red

object CategoryDataSource {
    val categories = listOf("Work", "Study", "Workout", "Hobby")
    val categoryIcons = mapOf(
        categories[0] to R.drawable.icon_monitor_circle,
        categories[1] to R.drawable.icon_book_circle,
        categories[2] to R.drawable.icon_barbell_circle,
        categories[3] to R.drawable.icon_code_circle
    )
    val categoryColors = mapOf(
        categories[0] to Purple.copy(alpha = 0.15f),
        categories[1] to Green.copy(alpha = 0.15f),
        categories[2] to Orange.copy(alpha = 0.15f),
        categories[3] to Red.copy(alpha = 0.15f)
    )

    fun getTextColor(category: String) : Color {
        return when(category) {
            categories[0] -> Purple
            categories[1] -> Green
            categories[2] -> Orange
            categories[3] -> Red
            else -> Purple
        }
    }
}