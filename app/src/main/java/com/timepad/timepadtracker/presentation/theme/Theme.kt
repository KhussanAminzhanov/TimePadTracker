package com.timepad.timepadtracker.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Black,
    primaryVariant = Black,
    onPrimary = White,
    secondary = White,
    secondaryVariant = White,
    onSecondary = Black,
    surface = DarkBlue,
    onSurface = White,
    background = Black,
    onBackground = White,
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = White,
    onPrimary = Black,
    secondary = Black,
    secondaryVariant = Black,
    onSecondary = White,
    surface = White,
    onSurface = Black,
    background = LightGray,
    onBackground = Black,
)

@Composable
fun TimePadTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}