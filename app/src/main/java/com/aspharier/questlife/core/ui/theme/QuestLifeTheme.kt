package com.aspharier.questlife.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun QuestLifeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val gameColors = if (darkTheme) DarkGameColors else LightGameColors

    CompositionLocalProvider(LocalGameColors provides gameColors) {
        MaterialTheme(
                colorScheme = colorScheme,
                typography = QuestLifeTypography,
                shapes = QuestLifeShapes,
                content = content
        )
    }
}
