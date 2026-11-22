package com.example.destination.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(

    primary = GreenPrimary,
    onPrimary = SurfaceWhite,
    primaryContainer = GreenLight,
    onPrimaryContainer = GreenDark,

    secondary = GreenSecondary,
    onSecondary = SurfaceWhite,
    secondaryContainer = GreenLight,
    onSecondaryContainer = GreenDark,

    tertiary = TealAccent,
    onTertiary = SurfaceWhite,

    background = BackgroundLight,
    onBackground = TextDark,

    surface = SurfaceWhite,
    onSurface = TextDark,

    error = ErrorRed
)

private val DarkColorScheme = darkColorScheme(

    primary = GreenLight,
    onPrimary = GreenDark,
    primaryContainer = GreenDark,
    onPrimaryContainer = GreenLight,

    secondary = GreenLight,
    onSecondary = GreenDark,

    tertiary = TealAccent,
    background = TextDark,
    onBackground = SurfaceWhite,

    surface = GreenDark,
    onSurface = SurfaceWhite
)

@Composable
fun DestinationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}