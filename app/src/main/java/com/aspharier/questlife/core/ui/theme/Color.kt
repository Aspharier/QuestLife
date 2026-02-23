package com.aspharier.questlife.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Brand palette
val primaryLight = Color(0xFF6366F1) // Indigo
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFE0E7FF)
val onPrimaryContainerLight = Color(0xFF1E1B4B)

val secondaryLight = Color(0xFFEC4899) // Pink
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFFCE7F3)
val onSecondaryContainerLight = Color(0xFF831843)

val tertiaryLight = Color(0xFF8B5CF6) // Purple
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFEDE9FE)
val onTertiaryContainerLight = Color(0xFF3B0764)

val backgroundLight = Color(0xFFF8FAFC)
val onBackgroundLight = Color(0xFF0F172A)
val surfaceLight = Color(0xFFFFFFFF)
val onSurfaceLight = Color(0xFF1E293B)
val onSurfaceVariantLight = Color(0xFF64748B)
val outlineLight = Color(0xFFCBD5E1)

val errorLight = Color(0xFFEF4444)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFEE2E2)
val onErrorContainerLight = Color(0xFF7F1D1D)

// Dark theme
val primaryDark = Color(0xFF818CF8) // Indigo lighter
val onPrimaryDark = Color(0xFF1E1B4B)
val primaryContainerDark = Color(0xFF312E81)
val onPrimaryContainerDark = Color(0xFFE0E7FF)

val secondaryDark = Color(0xFFF472B6)
val onSecondaryDark = Color(0xFF500724)
val secondaryContainerDark = Color(0xFF831843)
val onSecondaryContainerDark = Color(0xFFFCE7F3)

val tertiaryDark = Color(0xFFA78BFA)
val onTertiaryDark = Color(0xFF2E1065)
val tertiaryContainerDark = Color(0xFF4C1D95)
val onTertiaryContainerDark = Color(0xFFEDE9FE)

val backgroundDark = Color(0xFF0F172A)
val onBackgroundDark = Color(0xFFE2E8F0)
val surfaceDark = Color(0xFF1E293B)
val onSurfaceDark = Color(0xFFE2E8F0)
val onSurfaceVariantDark = Color(0xFF94A3B8)
val outlineDark = Color(0xFF334155)

val errorDark = Color(0xFFF87171)
val onErrorDark = Color(0xFF450A0A)
val errorContainerDark = Color(0xFF7F1D1D)
val onErrorContainerDark = Color(0xFFFEE2E2)

// Accent colors
val xpGold = Color(0xFFFFD700)
val streakFlame = Color(0xFFFF6B35)

// Color schemes
val LightColorScheme =
        lightColorScheme(
                primary = primaryLight,
                onPrimary = onPrimaryLight,
                primaryContainer = primaryContainerLight,
                onPrimaryContainer = onPrimaryContainerLight,
                secondary = secondaryLight,
                onSecondary = onSecondaryLight,
                secondaryContainer = secondaryContainerLight,
                onSecondaryContainer = onSecondaryContainerLight,
                tertiary = tertiaryLight,
                onTertiary = onTertiaryLight,
                tertiaryContainer = tertiaryContainerLight,
                onTertiaryContainer = onTertiaryContainerLight,
                background = backgroundLight,
                onBackground = onBackgroundLight,
                surface = surfaceLight,
                onSurface = onSurfaceLight,
                onSurfaceVariant = onSurfaceVariantLight,
                outline = outlineLight,
                error = errorLight,
                onError = onErrorLight,
                errorContainer = errorContainerLight,
                onErrorContainer = onErrorContainerLight,
        )

val DarkColorScheme =
        darkColorScheme(
                primary = primaryDark,
                onPrimary = onPrimaryDark,
                primaryContainer = primaryContainerDark,
                onPrimaryContainer = onPrimaryContainerDark,
                secondary = secondaryDark,
                onSecondary = onSecondaryDark,
                secondaryContainer = secondaryContainerDark,
                onSecondaryContainer = onSecondaryContainerDark,
                tertiary = tertiaryDark,
                onTertiary = onTertiaryDark,
                tertiaryContainer = tertiaryContainerDark,
                onTertiaryContainer = onTertiaryContainerDark,
                background = backgroundDark,
                onBackground = onBackgroundDark,
                surface = surfaceDark,
                onSurface = onSurfaceDark,
                onSurfaceVariant = onSurfaceVariantDark,
                outline = outlineDark,
                error = errorDark,
                onError = onErrorDark,
                errorContainer = errorContainerDark,
                onErrorContainer = onErrorContainerDark,
        )
