package com.aspharier.questlife.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── Game-specific semantic colors ──────────────────────────────────────────────

val healthRed = Color(0xFFFF4757)
val healthRedDark = Color(0xFFCC3945)
val manaBlue = Color(0xFF3B82F6)
val manaBlueDark = Color(0xFF5B9BF7)
val xpGold = Color(0xFFFFD700)
val xpGoldDark = Color(0xFFFFC107)
val shieldSilver = Color(0xFFB0BEC5)
val streakFlame = Color(0xFFFF6B35)
val staminaGreen = Color(0xFF22C55E)

val rareBlue = Color(0xFF3B82F6)
val epicPurple = Color(0xFF8B5CF6)
val legendaryOrange = Color(0xFFF97316)
val commonGray = Color(0xFF9E9E9E)

// ── Light Theme Palette ────────────────────────────────────────────────────────

val LightColorScheme =
        lightColorScheme(
                primary = Color(0xFF4F46E5), // Indigo 600
                onPrimary = Color(0xFFFFFFFF),
                primaryContainer = Color(0xFFE0E7FF),
                onPrimaryContainer = Color(0xFF3730A3),
                secondary = Color(0xFFDB2777), // Pink 600
                onSecondary = Color(0xFFFFFFFF),
                secondaryContainer = Color(0xFFFCE7F3),
                onSecondaryContainer = Color(0xFF831843),
                tertiary = Color(0xFF7C3AED), // Violet 600
                onTertiary = Color(0xFFFFFFFF),
                tertiaryContainer = Color(0xFFF5F3FF),
                onTertiaryContainer = Color(0xFF4C1D95),
                background = Color(0xFFF1F0F7), // Subtle lavender-gray
                onBackground = Color(0xFF1A1A2E),
                surface = Color(0xFFFFFFFF),
                onSurface = Color(0xFF1A1A2E),
                surfaceVariant = Color(0xFFEDE9FE),
                onSurfaceVariant = Color(0xFF4B5563),
                outline = Color(0xFFD1D5DB),
                error = Color(0xFFDC2626),
                onError = Color(0xFFFFFFFF),
                errorContainer = Color(0xFFFEE2E2),
                onErrorContainer = Color(0xFF991B1B),
        )

// ── Dark Theme Palette (Deep RPG Fantasy) ──────────────────────────────────────

val DarkColorScheme =
        darkColorScheme(
                primary = Color(0xFF818CF8), // Indigo 400
                onPrimary = Color(0xFF1E1B4B),
                primaryContainer = Color(0xFF312E81),
                onPrimaryContainer = Color(0xFFE0E7FF),
                secondary = Color(0xFFF472B6), // Pink 400
                onSecondary = Color(0xFF500724),
                secondaryContainer = Color(0xFF831843),
                onSecondaryContainer = Color(0xFFFCE7F3),
                tertiary = Color(0xFFA78BFA), // Violet 400
                onTertiary = Color(0xFF2E1065),
                tertiaryContainer = Color(0xFF5B21B6),
                onTertiaryContainer = Color(0xFFEDE9FE),
                background = Color(0xFF0A0E1A), // Deep navy-black
                onBackground = Color(0xFFE8E5F0),
                surface = Color(0xFF121829), // Slate panel
                onSurface = Color(0xFFE8E5F0),
                surfaceVariant = Color(0xFF1C2333),
                onSurfaceVariant = Color(0xFF9CA3AF),
                outline = Color(0xFF2D3548),
                error = Color(0xFFF87171),
                onError = Color(0xFF450A0A),
                errorContainer = Color(0xFF7F1D1D),
                onErrorContainer = Color(0xFFFEE2E2),
        )

// ── Extended Game Colors (via CompositionLocal) ────────────────────────────────

@Immutable
data class GameColors(
        val panelBackground: Color,
        val panelBorder: Color,
        val panelBorderGlow: Color,
        val healthBar: Color,
        val manaBar: Color,
        val xpBar: Color,
        val xpBarSecondary: Color,
        val staminaBar: Color,
        val streakFlame: Color,
        val rarityCommon: Color,
        val rarityRare: Color,
        val rarityEpic: Color,
        val rarityLegendary: Color,
        val accentGlow: Color,
        val fabGlow: Color,
        val navBarBackground: Color,
        val navBarBorder: Color,
        val navBarSelected: Color,
        val navBarUnselected: Color,
        val sectionDivider: Color,
        val chipSelected: Color,
        val chipBorder: Color,
        val completedGreen: Color,
)

val LightGameColors =
        GameColors(
                panelBackground = Color(0xFFF8F7FF),
                panelBorder = Color(0xFFD1CBE8),
                panelBorderGlow = Color(0x334F46E5),
                healthBar = healthRed,
                manaBar = manaBlue,
                xpBar = xpGold,
                xpBarSecondary = Color(0xFFFFA000),
                staminaBar = staminaGreen,
                streakFlame = streakFlame,
                rarityCommon = commonGray,
                rarityRare = rareBlue,
                rarityEpic = epicPurple,
                rarityLegendary = legendaryOrange,
                accentGlow = Color(0x224F46E5),
                fabGlow = Color(0xFF4F46E5),
                navBarBackground = Color(0xFFF0EEF9),
                navBarBorder = Color(0xFFD1CBE8),
                navBarSelected = Color(0xFF4F46E5),
                navBarUnselected = Color(0xFF9CA3AF),
                sectionDivider = Color(0x33818CF8),
                chipSelected = Color(0x224F46E5),
                chipBorder = Color(0xFFD1D5DB),
                completedGreen = staminaGreen,
        )

val DarkGameColors =
        GameColors(
                panelBackground = Color(0xFF141B2D),
                panelBorder = Color(0xFF2A3350),
                panelBorderGlow = Color(0x44818CF8),
                healthBar = Color(0xFFFF4757),
                manaBar = Color(0xFF5B9BF7),
                xpBar = Color(0xFFFFD700),
                xpBarSecondary = Color(0xFFFFA000),
                staminaBar = Color(0xFF34D399),
                streakFlame = Color(0xFFFF8C57),
                rarityCommon = Color(0xFF9E9E9E),
                rarityRare = Color(0xFF60A5FA),
                rarityEpic = Color(0xFFA78BFA),
                rarityLegendary = Color(0xFFFB923C),
                accentGlow = Color(0x33818CF8),
                fabGlow = Color(0xFF818CF8),
                navBarBackground = Color(0xFF0D1220),
                navBarBorder = Color(0xFF1E2A45),
                navBarSelected = Color(0xFF818CF8),
                navBarUnselected = Color(0xFF4B5563),
                sectionDivider = Color(0x33818CF8),
                chipSelected = Color(0x33818CF8),
                chipBorder = Color(0xFF2D3548),
                completedGreen = Color(0xFF34D399),
        )

val LocalGameColors = staticCompositionLocalOf { DarkGameColors }
