package com.aspharier.questlife.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun QuestLifeTheme(themeType: ThemeType = ThemeType.DEEP_DARK, content: @Composable () -> Unit) {
    val colorScheme = when (themeType) {
        ThemeType.DEEP_DARK -> DarkColorScheme
        ThemeType.MYSTIC_PURPLE -> PurpleDarkColorScheme
        ThemeType.DARK_GREEN -> GreenDarkColorScheme
    }
    val gameColors = when (themeType) {
        ThemeType.DEEP_DARK -> DarkGameColors
        ThemeType.MYSTIC_PURPLE -> PurpleDarkGameColors
        ThemeType.DARK_GREEN -> GreenDarkGameColors
    }
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
            
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false
        }
    }

    CompositionLocalProvider(LocalGameColors provides gameColors) {
        MaterialTheme(
                colorScheme = colorScheme,
                typography = QuestLifeTypography,
                shapes = QuestLifeShapes,
                content = content
        )
    }
}
