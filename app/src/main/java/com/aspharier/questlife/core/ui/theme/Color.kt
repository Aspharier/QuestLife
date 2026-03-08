package com.aspharier.questlife.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Brand palette
// Brand palette - More vibrant HSL-tailored colors
val primaryLight = Color(0xFF4F46E5) // Indigo 600
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFEEF2FF)
val onPrimaryContainerLight = Color(0xFF3730A3)

val secondaryLight = Color(0xFFDB2777) // Pink 600
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFFCE7F3)
val onSecondaryContainerLight = Color(0xFF831843)

val tertiaryLight = Color(0xFF7C3AED) // Violet 600
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFF5F3FF)
val onTertiaryContainerLight = Color(0xFF4C1D95)

val backgroundLight = Color(0xFFF9FAFB) // Gray 50
val onBackgroundLight = Color(0xFF111827) // Gray 900
val surfaceLight = Color(0xFFFFFFFF)
val onSurfaceLight = Color(0xFF1F2937) // Gray 800
val onSurfaceVariantLight = Color(0xFF4B5563) // Gray 600
val outlineLight = Color(0xFFE5E7EB) // Gray 200

val errorLight = Color(0xFFDC2626)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFEE2E2)
val onErrorContainerLight = Color(0xFF991B1B)

// Glass variants Light - Optimized for visibility
val glassWhite = Color(0xCCFFFFFF) // Increased alpha
val glassIndigo = Color(0x1A4F46E5)
val glassSurface = Color(0xD9F9FAFB) // Increased alpha for better readability

// Premium Gradients Light
val gradPrimary = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
val gradSecondary = listOf(Color(0xFFEC4899), Color(0xFFF43F5E))
val gradSurface = listOf(Color(0xFFFFFFFF), Color(0xFFF1F5F9))

// Dark theme
// Dark theme - Deep slate and vibrant indigo
val primaryDark = Color(0xFF818CF8) // Indigo 400
val onPrimaryDark = Color(0xFF1E1B4B)
val primaryContainerDark = Color(0xFF3730A3)
val onPrimaryContainerDark = Color(0xFFE0E7FF)

val secondaryDark = Color(0xFFF472B6) // Pink 400
val onSecondaryDark = Color(0xFF500724)
val secondaryContainerDark = Color(0xFF831843)
val onSecondaryContainerDark = Color(0xFFFCE7F3)

val tertiaryDark = Color(0xFFA78BFA) // Violet 400
val onTertiaryDark = Color(0xFF2E1065)
val tertiaryContainerDark = Color(0xFF5B21B6)
val onTertiaryContainerDark = Color(0xFFEDE9FE)

val backgroundDark = Color(0xFF030712) // Gray 950
val onBackgroundDark = Color(0xFFF9FAFB) // Gray 50
val surfaceDark = Color(0xFF111827) // Gray 900
val onSurfaceDark = Color(0xFFF3F4F6) // Gray 100
val onSurfaceVariantDark = Color(0xFF9CA3AF) // Gray 400
val outlineDark = Color(0xFF374151) // Gray 700

val errorDark = Color(0xFFF87171)
val onErrorDark = Color(0xFF450A0A)
val errorContainerDark = Color(0xFF7F1D1D)
val onErrorContainerDark = Color(0xFFFEE2E2)

// Glass variants Dark
val glassBlack = Color(0xCC030712)
val glassIndigoDark = Color(0x33818CF8)
val glassSurfaceDark = Color(0xB3111827) // Increased alpha

// Premium Gradients Dark
val gradPrimaryDark = listOf(Color(0xFF4F46E5), Color(0xFF7C3AED))
val gradSurfaceDark = listOf(Color(0xFF1E293B), Color(0xFF0F172A))

// Accent colors & Rarities
val xpGold = Color(0xFFFFD700)
val streakFlame = Color(0xFFFF6B35)

val rareBlue = Color(0xFF3B82F6)
val epicPurple = Color(0xFF8B5CF6)
val legendaryOrange = Color(0xFFF97316)

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
